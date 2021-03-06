package home.stetsenko.processing;

import home.stetsenko.exceptions.CellCalculationException;
import home.stetsenko.exceptions.NonExistingReferenceException;
import home.stetsenko.model.cell.*;
import home.stetsenko.model.row.Row;
import home.stetsenko.model.sheet.Sheet;
import home.stetsenko.processing.operations.OperationProcessor;
import home.stetsenko.processing.operations.OperationProcessorFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

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

                if (CellType.CELL_TYPE_STRING.equals(cellType)) {

                    CellValue cellValue = cell.getCellValue();
                    cellValue.setTextValue(cell.isCalculated() ?
                            cellValue.getTextValue() : CellParser.extractText(cellValue.getTextValue()));
                    cell.setCalculated(true);

                } else if (CellType.CELL_TYPE_EXPRESSION.equals(cellType)) {

                    ReturnedTypeValue result;
                    try {
                        //for detecting circular references
                        Set<CellReference> cellRefsChain = new HashSet<>();

                        result = getCellResult(sheet, cell, cellRefsChain);
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
                    } catch (CellCalculationException e) {
                        ExpressionError expressionError = e.getExpressionError();
                        cell.setCellType(CellType.CELL_TYPE_ERROR);
                        cell.getCellValue().setCellErrorValue(expressionError);
                        cell.setCalculated(true);
                    } catch (NonExistingReferenceException e) {
                        ExpressionError expressionError = e.getExpressionError();
                        cell.setCellType(CellType.CELL_TYPE_ERROR);
                        cell.getCellValue().setCellErrorValue(expressionError);
                        cell.setCalculated(true);
                    }


                }
            }
        }

        LOGGER.debug("=========== End processing cells ===========");

        return sheet;
    }

    private ReturnedTypeValue getCellResult(Sheet sheet, Cell cell, Set<CellReference> cellRefsChain)
            throws CellCalculationException, NonExistingReferenceException {

        if ( !cellRefsChain.add(cell.getCellReference()) ) {
            LOGGER.error("Circular reference detected");
            throw new CellCalculationException(ExpressionError.CIRCULAR_REF, "circular reference");
        }

        int result = 0;

        CellType cellType = cell.getCellType();
        if (CellType.CELL_TYPE_NUMERIC.equals(cellType)) {

            //do nothing, only return numeric value
            ReturnedTypeValue returnedTypeValue = new ReturnedTypeValue(cell.getCellValue().getNumericValue());
            cell.setCalculated(true);

            return returnedTypeValue;

        } else if (CellType.CELL_TYPE_ERROR.equals(cellType)) {

            //if current cell has reference to error cell, mark current cell as #REF!
            throw new CellCalculationException(ExpressionError.REF, "cell has reference to error ref");

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
                    ReturnedTypeValue cellResult = getCellResult(sheet, cell1, cellRefsChain);
                    if (ReturnedTypeValue.Type.CELL_TYPE_NUMERIC.equals(cellResult.getType())) {
                        cell1.setCellType(CellType.CELL_TYPE_NUMERIC);
                        cell1.getCellValue().setNumericValue(cellResult.getNumericValue());
                    } else if (ReturnedTypeValue.Type.CELL_TYPE_STRING.equals(cellResult.getType())) {
                        cell1.setCellType(CellType.CELL_TYPE_STRING);
                        cell1.getCellValue().setTextValue(cellResult.getTextValue());
                    }
                    cell1.setCalculated(true);
                    return cellResult;
                }
            }

            //if expression has operations, do calculations
            for (int i = 0; i < operationList.size(); i++) {
                Term leftTerm = (i==0) ? termList.get(i) : new Term(String.valueOf(result));
                Term rightTerm = termList.get(i+1);
                result = calculateRecursive(sheet, leftTerm, rightTerm, operationList.get(i), cellRefsChain);
            }
        }

        return new ReturnedTypeValue(result);
    }

    private int calculateRecursive(Sheet sheet, Term leftTerm, Term rightTerm, String operation,
                                   Set<CellReference> cellRefsChain) throws CellCalculationException, NonExistingReferenceException {

        TermType leftTermType = leftTerm.getTermType();
        TermType rightTermType = rightTerm.getTermType();

        OperationProcessor operationProcessor = operationProcessorFactory.getOperationProcessor(operation);

        if (TermType.TERM_TYPE_NUMERIC.equals(leftTermType)
                && TermType.TERM_TYPE_NUMERIC.equals(rightTermType)) {
            //can be calculated
            return operationProcessor.calculate(leftTerm.getNumericValue(), rightTerm.getNumericValue());
        }

        ReturnedTypeValue leftValue = getTermValue(sheet, leftTerm, leftTermType, cellRefsChain);
        ReturnedTypeValue rightValue = getTermValue(sheet, rightTerm, rightTermType, cellRefsChain);

        //calculation can be processed only with numeric values
        //calculation with text values is unsupported
        if ( ReturnedTypeValue.Type.CELL_TYPE_STRING.equals(leftValue.getType())
                || ReturnedTypeValue.Type.CELL_TYPE_STRING.equals(rightValue.getType()) ) {
            throw new CellCalculationException(ExpressionError.REF, "text can be calculated");
        }

        return operationProcessor.calculate(leftValue.getNumericValue(), rightValue.getNumericValue());

    }

    private ReturnedTypeValue getTermValue(Sheet sheet, Term term, TermType termType, Set<CellReference> cellRefsChain)
            throws CellCalculationException, NonExistingReferenceException {

        int value = 0;

        if (TermType.TERM_TYPE_CELL_REFERENCE.equals(termType)) {
            CellReference cellReferenceValue = term.getCellReferenceValue();
            Cell cell = sheet.getRow(cellReferenceValue.getRowIndex()).getCell(cellReferenceValue.getColIndex());

            if ( !cellRefsChain.add(cell.getCellReference()) ) {
                LOGGER.error("Circular reference detected");
                throw new CellCalculationException(ExpressionError.CIRCULAR_REF, "circular reference");
            }

            CellType cellType = cell.getCellType();
            if (CellType.CELL_TYPE_NUMERIC.equals(cellType)) {

                return new ReturnedTypeValue(cell.getCellValue().getNumericValue());

            } else if (CellType.CELL_TYPE_EXPRESSION.equals(cellType)) {
                ExpressionValue expressionValue = cell.getCellValue().getExpressionValue();

                List<Term> termList = expressionValue.getTermList();
                List<String> operationList = expressionValue.getOperationList();
                for (int i = 0; i < operationList.size(); i++) {
                    Term leftTerm1 = termList.get(i);
                    Term rightTerm1 = termList.get(i + 1);
                    value += calculateRecursive(sheet, leftTerm1, rightTerm1, operationList.get(i), cellRefsChain);
                }
                cell.getCellValue().setNumericValue(value);
                cell.setCellType(CellType.CELL_TYPE_NUMERIC);
                cell.setCalculated(true);
            } else if (CellType.CELL_TYPE_STRING.equals(cellType)) {

                return new ReturnedTypeValue(cell.isCalculated() ?
                        cell.getCellValue().getTextValue() : CellParser.extractText(cell.getCellValue().getTextValue()));

            } else if (CellType.CELL_TYPE_BLANK.equals(cellType) || CellType.CELL_TYPE_ERROR.equals(cellType)) {
                throw new CellCalculationException(ExpressionError.REF, "unsupported");
            }
        } else if (TermType.TERM_TYPE_NUMERIC.equals(termType)) {
            return new ReturnedTypeValue(term.getNumericValue());
        }
        return new ReturnedTypeValue(value);
    }

    static class ReturnedTypeValue {

        private Type type;
        private int numericValue;
        private String textValue;

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
