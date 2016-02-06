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

        LOGGER.debug("=== Start processing cells ===");

        Iterator<Row> rowIterator = sheet.rowIterator();

        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Iterator<Cell> cellIterator = row.cellIterator();
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                LOGGER.debug("Cell to be processed = {}", cell);
                CellType cellType = cell.getCellType();
                if (CellType.CELL_TYPE_NUMERIC.equals(cellType)) {
                    cell.setCalculated(true);
                } else if (CellType.CELL_TYPE_STRING.equals(cellType)) {

                    CellValue cellValue = cell.getCellValue();
                    cellValue.setTextValue(CellParser.extractText(cellValue.getTextValue()));
                    cell.setCalculated(true);

                } else if (CellType.CELL_TYPE_EXPRESSION.equals(cellType)) {

                    int result = getCellResult(sheet, cell);
                    cell.setCellType(CellType.CELL_TYPE_NUMERIC);
                    cell.getCellValue().setNumericValue(result);
                    cell.setCalculated(true);

                }
            }
        }

        LOGGER.debug("=== Start processing cells ===");

        return sheet;
    }

    private int getCellResult(Sheet sheet, Cell cell) {

        int result = 0;

        CellType cellType = cell.getCellType();
        if (CellType.CELL_TYPE_NUMERIC.equals(cellType)) {
            int numericValue = cell.getCellValue().getNumericValue();
            cell.setCellType(CellType.CELL_TYPE_NUMERIC);
            cell.getCellValue().setNumericValue(numericValue);
            cell.setCalculated(true);
            return numericValue;
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
                    return term.getNumericValue();
                } else if (TermType.TERM_TYPE_CELL_REFERENCE.equals(termType)) {
                    CellReference cellReferenceValue = term.getCellReferenceValue();
                    Cell cell1 = sheet.getRow(cellReferenceValue.getRowIndex()).getCell(cellReferenceValue.getColIndex());
                    int cellResult = getCellResult(sheet, cell1);
                    cell1.setCellType(CellType.CELL_TYPE_NUMERIC);
                    cell1.getCellValue().setNumericValue(cellResult);
                    return cellResult;
                }
            }

            for (int i = 0; i < operationList.size(); i++) {
                Term leftTerm = termList.get(i);
                Term rightTerm = termList.get(i+1);
                result += calculateRecursive(sheet, leftTerm, rightTerm, operationList.get(i));
            }
        }

        return result;
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

}
