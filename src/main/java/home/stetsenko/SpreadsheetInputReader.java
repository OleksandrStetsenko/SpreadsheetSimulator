package home.stetsenko;

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

    public void readInput() {

        if (stdin.hasNextLine()) {
            String[] values = stdin.nextLine().split(SpreadsheetConstants.SEPARATOR);
            if (values.length != 2) {
                LOGGER.error(SpreadsheetConstants.MESSAGE_NO_LENGTH_OR_HEIGHT);
                return;
            }

            int i = 0;
            sheet = new SheetImpl();
            while (stdin.hasNextLine()) {
                String line = stdin.nextLine();
                String[] lineValues = line.split(SpreadsheetConstants.SEPARATOR, -1);

                Row row = sheet.createRow(i);

                int j = 0;
                for (String v : lineValues) {

                    Cell cell = row.createCell(j);

                    CellType currentCelltype = CellParser.recognizeType(v);
                    cell.setCellReference(new CellReference(i, j));
                    if (CellType.CELL_TYPE_EXPRESSION.equals(currentCelltype)) {
                        cell.setCellType(CellType.CELL_TYPE_EXPRESSION);
                        ExpressionValue expressionValue = new ExpressionValue(v);
                        cell.setCellValue(new CellValue().setExpressionValue(expressionValue));
                    } else if (CellType.CELL_TYPE_NUMERIC.equals(currentCelltype)) {
                        cell.setCellType(CellType.CELL_TYPE_NUMERIC);
                        cell.setCellValue(new CellValue().setNumericValue(Integer.parseInt(v)));
                    } else if (CellType.CELL_TYPE_STRING.equals(currentCelltype)) {
                        cell.setCellType(CellType.CELL_TYPE_STRING);
                        cell.setCellValue(new CellValue().setTextValue(v));
                    }
                    //todo: add other cell types

                    j++;
                }
                i++;
            }
        }

    }

    public Sheet getSheet() {
        return sheet;
    }
}
