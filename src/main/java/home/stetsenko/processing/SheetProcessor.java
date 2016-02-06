package home.stetsenko.processing;

import home.stetsenko.model.cell.*;
import home.stetsenko.model.row.Row;
import home.stetsenko.model.sheet.Sheet;
import home.stetsenko.processing.operations.OperationProcessor;
import home.stetsenko.processing.operations.OperationProcessorFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.List;

public class SheetProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(SheetProcessor.class);
    OperationProcessorFactory operationProcessorFactory = new OperationProcessorFactory();

    public Sheet process(Sheet sheet) {

        LOGGER.debug("=========== Start processing cells ===========");

        Iterator<Row> rowIterator = sheet.rowIterator();

        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Iterator<Cell> cellIterator = row.cellIterator();
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                LOGGER.debug("Cell to be processed = {}", cell);

                CellType cellType = cell.getCellType();
                //processed should be only expressions and strings
                if (CellType.CELL_TYPE_STRING.equals(cellType)) {

                    CellValue cellValue = cell.getCellValue();
                    cellValue.setTextValue(CellParser.extractText(cellValue.getTextValue()));
                    cell.setCalculated(true);

                } else if (CellType.CELL_TYPE_EXPRESSION.equals(cellType)) {

                    ReturnedTypeValue result = getCellResult(sheet, cell);
                    ReturnedTypeValue.Type type = result.getType();
                    if (ReturnedTypeValue.Type.CELL_TYPE_NUMERIC.equals(type)) {
                        cell.setCellType(CellType.CELL_TYPE_NUMERIC);
                        cell.getCellValue().setNumericValue(result.getNumericValue());
                        cell.setCalculated(true);
                    } else if (ReturnedTypeValue.Type.CELL_TYPE_STRING.equals(type)) {
                        cell.setCellType(CellType.CELL_TYPE_STRING);
                        cell.getCellValue().setTextValue(result.getTextValue());
                        cell.setCalculated(true);
                    }

                }
            }
        }

        LOGGER.debug("=========== End processing cells ===========");

        return sheet;
    }

    private ReturnedTypeValue getCellResult(Sheet sheet, Cell cell) {

        int result = 0;

        CellType cellType = cell.getCellType();
        if (CellType.CELL_TYPE_NUMERIC.equals(cellType)) {

            //do nothing, only return numeric value
            ReturnedTypeValue returnedTypeValue = new ReturnedTypeValue(cell.getCellValue().getNumericValue());
            cell.setCalculated(true);

            return returnedTypeValue;

        } else if (CellType.CELL_TYPE_STRING.equals(cellType)) {

            //do nothing, only return string value
            ReturnedTypeValue returnedTypeValue = new ReturnedTypeValue(cell.isCalculated() ?
                    cell.getCellValue().getTextValue() : CellParser.extractText(cell.getCellValue().getTextValue()));

            cell.setCalculated(true);

            return returnedTypeValue;

        } else if (CellType.CELL_TYPE_EXPRESSION.equals(cellType)) {

            CellValue cellValue = cell.getCellValue();
            ExpressionValue expressionValue = cellValue.getExpressionValue();
            List<Term> termList = expressionValue.getTermList();
            List<String> operationList = expressionValue.getOperationList();

            if (operationList.size() == 0 && termList.size() == 1) {
                // without operations (ref to another cell)
                Term term = termList.get(0);
                TermType termType = term.getTermType();
                if (TermType.TERM_TYPE_NUMERIC.equals(termType)) {
                    return new ReturnedTypeValue(term.getNumericValue());
                } else if (TermType.TERM_TYPE_CELL_REFERENCE.equals(termType)) {
                    CellReference cellReferenceValue = term.getCellReferenceValue();
                    Cell cell1 = sheet.getRow(cellReferenceValue.getRowIndex()).getCell(cellReferenceValue.getColIndex());
                    ReturnedTypeValue cellResult = getCellResult(sheet, cell1);
                    if (ReturnedTypeValue.Type.CELL_TYPE_NUMERIC.equals(cellResult)) {
                        cell1.setCellType(CellType.CELL_TYPE_NUMERIC);
                        cell1.getCellValue().setNumericValue(cellResult.getNumericValue());
                        cell1.setCalculated(true);
                    } else if (ReturnedTypeValue.Type.CELL_TYPE_STRING.equals(cellResult)) {
                        cell1.setCellType(CellType.CELL_TYPE_STRING);
                        cell1.getCellValue().setTextValue(cellResult.getTextValue());
                        cell1.setCalculated(true);
                    }
                    return cellResult;
                }
            }

            for (int i = 0; i < operationList.size(); i++) {
                Term leftTerm = termList.get(i);
                Term rightTerm = termList.get(i+1);
                result += calculateRecursive(sheet, leftTerm, rightTerm, operationList.get(i));
            }
        }

        return new ReturnedTypeValue(result);
    }

    private int calculateRecursive(Sheet sheet, Term leftTerm, Term rightTerm, String operation) {

        TermType leftTermType = leftTerm.getTermType();
        TermType rightTermType = rightTerm.getTermType();

        OperationProcessor operationProcessor = operationProcessorFactory.getOperationProcessor(operation);

        if (TermType.TERM_TYPE_NUMERIC.equals(leftTermType)
                && TermType.TERM_TYPE_NUMERIC.equals(rightTermType)) {
            //can be calculated
            return operationProcessor.calculate(leftTerm.getNumericValue(), rightTerm.getNumericValue());
        }

        int leftValue = getTermValue(sheet, leftTerm, leftTermType);
        int rightValue = getTermValue(sheet, rightTerm, rightTermType);

        return operationProcessor.calculate(leftValue, rightValue);

    }

    private int getTermValue(Sheet sheet, Term term, TermType termType) {
        int value = 0;

        if (TermType.TERM_TYPE_CELL_REFERENCE.equals(termType)) {
            CellReference cellReferenceValue = term.getCellReferenceValue();
            Cell cell = sheet.getRow(cellReferenceValue.getRowIndex()).getCell(cellReferenceValue.getColIndex());
            CellType cellType = cell.getCellType();
            if (CellType.CELL_TYPE_NUMERIC.equals(cellType)) {
                value = cell.getCellValue().getNumericValue();
            } else if (CellType.CELL_TYPE_EXPRESSION.equals(cellType)) {
                ExpressionValue expressionValue = cell.getCellValue().getExpressionValue();

                List<Term> termList = expressionValue.getTermList();
                List<String> operationList = expressionValue.getOperationList();
                for (int i = 0; i < operationList.size(); i++) {
                    Term leftTerm1 = termList.get(i);
                    Term rightTerm1 = termList.get(i+1);
                    value += calculateRecursive(sheet, leftTerm1, rightTerm1, operationList.get(i));
                }
                cell.getCellValue().setNumericValue(value);
                cell.setCellType(CellType.CELL_TYPE_NUMERIC);
                cell.setCalculated(true);
            }
        }
        return value;
    }

    static class ReturnedTypeValue {

        private Type type;
        private int numericValue;
        private String textValue;

        public ReturnedTypeValue(Type type, int numericValue, String textValue) {
            this.type = type;
            this.numericValue = numericValue;
            this.textValue = textValue;
        }

        public ReturnedTypeValue(int numericValue) {
            this.numericValue = numericValue;
            this.type = Type.CELL_TYPE_NUMERIC;
        }

        public ReturnedTypeValue(String textValue) {
            this.textValue = textValue;
            this.type = Type.CELL_TYPE_STRING;
        }

        public Type getType() {
            return type;
        }

        public int getNumericValue() {
            return numericValue;
        }

        public String getTextValue() {
            return textValue;
        }

        public enum Type {

            CELL_TYPE_NUMERIC(0),
            CELL_TYPE_STRING(1);

            private int value;

            Type(int value) {
                this.value = value;
            }
        }

    }

}
