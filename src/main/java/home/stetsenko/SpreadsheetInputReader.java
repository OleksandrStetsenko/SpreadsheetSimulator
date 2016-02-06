package home.stetsenko;

import home.stetsenko.exceptions.IllegalInputFormatException;
import home.stetsenko.model.cell.*;
import home.stetsenko.model.row.Row;
import home.stetsenko.model.sheet.Sheet;
import home.stetsenko.model.sheet.SheetImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class SpreadsheetInputReader {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpreadsheetInputReader.class);

    private Sheet sheet;
    private Scanner stdin;

    public SpreadsheetInputReader(Scanner stdin) {
        this.stdin = stdin;
    }

    public void readInput() throws IllegalInputFormatException {

        if (stdin.hasNextLine()) {
            String[] values = stdin.nextLine().split(SpreadsheetConstants.SEPARATOR);
            if (values.length != 2) {
                LOGGER.error(SpreadsheetConstants.MESSAGE_NO_LENGTH_OR_HEIGHT);
                throw new IllegalInputFormatException(SpreadsheetConstants.MESSAGE_NO_LENGTH_OR_HEIGHT);
            }

            int lineIndex = 0;
            sheet = new SheetImpl();
            while (stdin.hasNextLine()) {
                String line = stdin.nextLine();
                String[] lineValues = line.split(SpreadsheetConstants.SEPARATOR, -1);

                Row row = sheet.createRow(lineIndex);

                int cellIndex = 0;
                for (String v : lineValues) {

                    Cell cell = row.createCell(cellIndex);
                    cell.setCellReference(new CellReference(lineIndex, cellIndex));

                    CellType currentCellType = CellParser.recognizeType(v);
                    if (CellType.CELL_TYPE_EXPRESSION.equals(currentCellType)) {
                        cell.setCellType(CellType.CELL_TYPE_EXPRESSION);
                        ExpressionValue expressionValue = new ExpressionValue(v);
                        cell.setCellValue(new CellValue().setExpressionValue(expressionValue));
                    } else if (CellType.CELL_TYPE_NUMERIC.equals(currentCellType)) {
                        cell.setCellType(CellType.CELL_TYPE_NUMERIC);
                        cell.setCellValue(new CellValue().setNumericValue(Integer.parseInt(v)));
                    } else if (CellType.CELL_TYPE_STRING.equals(currentCellType)) {
                        cell.setCellType(CellType.CELL_TYPE_STRING);
                        cell.setCellValue(new CellValue().setTextValue(v));
                    } else if (CellType.CELL_TYPE_BLANK.equals(currentCellType)) {
                        cell.setCellType(CellType.CELL_TYPE_BLANK);
                        cell.setCellValue(new CellValue().setTextValue("<blank>"));
                    } else if (CellType.CELL_TYPE_ERROR.equals(currentCellType)) {
                        cell.setCellType(CellType.CELL_TYPE_ERROR);
                        cell.setCellValue(new CellValue().setCellErrorValue(ExpressionError.UNKNOWN_CELL_TYPE));
                    }
                    cellIndex++;

                }
                lineIndex++;
            }
        }

    }

    public Sheet getSheet() {
        return sheet;
    }
}
