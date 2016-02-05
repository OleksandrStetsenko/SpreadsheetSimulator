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

        Iterator<Row> rowIterator = sheet.rowIterator();

        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Iterator<Cell> cellIterator = row.cellIterator();
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                LOGGER.debug("cell = {}", cell);
                CellType cellType = cell.getCellType();
                if (CellType.CELL_TYPE_NUMERIC.equals(cellType)) {
                    cell.setCalculated(true);
                } else if (CellType.CELL_TYPE_STRING.equals(cellType)) {

                    cell.setCalculated(true);
                    CellValue cellValue = cell.getCellValue();
                    cellValue.setTextValue(CellParser.extractText(cellValue.getTextValue()));

                } else if (CellType.CELL_TYPE_EXPRESSION.equals(cellType)) {

                    CellValue cellValue = cell.getCellValue();
                    ExpressionValue expressionValue = cellValue.getExpressionValue();
                    List<Term> termList = expressionValue.getTermList();
                    List<String> operationList = expressionValue.getOperationList();

                    int result = 0;
                    for (int i = 0; i < operationList.size(); i++) {
                        int index = i;
                        Term leftTerm = termList.get(index);
                        Term rightTerm = termList.get(index++);
                        result += calculateRecursive(sheet, leftTerm, rightTerm, operationList.get(i));
                    }
                    cell.setCellType(CellType.CELL_TYPE_NUMERIC);
                    cellValue.setNumericValue(result);

                }
            }
        }

        return sheet;
    }

    private int calculateRecursive(Sheet sheet, Term leftTerm, Term rightTerm, String operation) {

        int result = 0;

        TermType leftTermTermType = leftTerm.getTermType();
        TermType rightTermTermType = rightTerm.getTermType();

        OperationProcessor operationProcessor = operationProcessorFactory.getOperationProcessor(operation);

        if (TermType.TERM_TYPE_NUMERIC.equals(leftTermTermType) && TermType.TERM_TYPE_NUMERIC.equals(rightTermTermType)) {
            //can be calculated
            return operationProcessor.calculate(leftTerm.getNumericValue(), rightTerm.getNumericValue());
        }

        int leftValue = 0;
        if (TermType.TERM_TYPE_CELL_REFERENCE.equals(leftTermTermType)) {
            CellReference cellReferenceValue = leftTerm.getCellReferenceValue();
            Cell cell = sheet.getRow(cellReferenceValue.getRowIndex()).getCell(cellReferenceValue.getColIndex());
            CellType cellType = cell.getCellType();
            if (CellType.CELL_TYPE_NUMERIC.equals(cellType)) {
                leftValue = cell.getCellValue().getNumericValue();
            } else if (CellType.CELL_TYPE_EXPRESSION.equals(cellType)) {
                ExpressionValue expressionValue = cell.getCellValue().getExpressionValue();

                List<Term> termList = expressionValue.getTermList();
                List<String> operationList = expressionValue.getOperationList();
                for (int i = 0; i < operationList.size(); i++) {
                    int index = i;
                    //todo rename
                    Term leftTerm1 = termList.get(index);
                    //todo rename
                    Term rightTerm1 = termList.get(index++);
                    leftValue = calculateRecursive(sheet, leftTerm1, rightTerm1, operationList.get(i));
                }

            }
        }

        int rightValue = 0;
        if (TermType.TERM_TYPE_CELL_REFERENCE.equals(leftTermTermType)) {
            CellReference cellReferenceValue = rightTerm.getCellReferenceValue();
            Cell cell = sheet.getRow(cellReferenceValue.getRowIndex()).getCell(cellReferenceValue.getColIndex());
            CellType cellType = cell.getCellType();
            if (CellType.CELL_TYPE_NUMERIC.equals(cellType)) {
                rightValue = cell.getCellValue().getNumericValue();
            } else if (CellType.CELL_TYPE_EXPRESSION.equals(cellType)) {
                ExpressionValue expressionValue = cell.getCellValue().getExpressionValue();

                List<Term> termList = expressionValue.getTermList();
                List<String> operationList = expressionValue.getOperationList();
                for (int i = 0; i < operationList.size(); i++) {
                    int index = i;
                    //todo rename
                    Term leftTerm1 = termList.get(index);
                    //todo rename
                    Term rightTerm1 = termList.get(index++);
                    rightValue = calculateRecursive(sheet, leftTerm1, rightTerm1, operationList.get(i));
                }

            }
        }

        result = operationProcessor.calculate(leftValue, rightValue);

        return result;

    }

}
