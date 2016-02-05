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

    public static Sheet process(Sheet sheet) {

        OperationProcessorFactory operationProcessorFactory = new OperationProcessorFactory();

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
                    for (int i = 0; i < operationList.size(); i++) {
                        Term leftTerm = termList.get(i);
                        TermType leftTermTermType = leftTerm.getTermType();
                        //todo
                        int k = i;
                        Term rightTerm = termList.get(i++);
                        TermType rightTermTermType = rightTerm.getTermType();

                        if (TermType.TERM_TYPE_NUMERIC.equals(leftTermTermType) && TermType.TERM_TYPE_NUMERIC.equals(rightTermTermType)) {
                            //can be calculated
                            OperationProcessor operationProcessor = operationProcessorFactory.getOperationProcessor(operationList.get(0));
                            int result = operationProcessor.calculate(leftTerm.getNumericValue(), rightTerm.getNumericValue());
                        }
                    }

                }
            }
        }

        return sheet;
    }

}
