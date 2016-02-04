package home.stetsenko;

import home.stetsenko.model.cell.Cell;
import home.stetsenko.model.cell.CellType;
import home.stetsenko.model.cell.CellValue;
import home.stetsenko.model.cell.ExpressionValue;
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
                    cell.setCellType(CellType.CELL_TYPE_EXPRESSION);
                    ExpressionValue expressionValue = new ExpressionValue(v);
                    cell.setCellValue(new CellValue().setExpressionValue(expressionValue));
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
