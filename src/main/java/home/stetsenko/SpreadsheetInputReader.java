package home.stetsenko;

import home.stetsenko.model.cell.Cell;
import home.stetsenko.model.cell.CellValue;
import home.stetsenko.model.row.Row;
import home.stetsenko.model.sheet.Sheet;
import home.stetsenko.model.sheet.SheetImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class SpreadsheetInputReader {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpreadsheetInputReader.class);

    private String[][] cells;
    private Sheet sheet;
    private Scanner stdin;

    public SpreadsheetInputReader(Scanner stdin) {
        this.stdin = stdin;
    }

    public void readInput() {

        if (stdin.hasNextLine()) {
            String[] values = stdin.nextLine().split(SpreadsheetConstants.SEPARATOR);
            if (values.length != 2) {
                LOGGER.error("There are no height or length of table");
                return;
            }
            cells = new String[Integer.parseInt(values[0])][Integer.parseInt(values[1])];

            int i = 0;
            sheet = new SheetImpl();
            while (stdin.hasNextLine()) {
                String line = stdin.nextLine();
                String[] lineValues = line.split(SpreadsheetConstants.SEPARATOR, -1);

                Row row = sheet.createRow(i);

                int j = 0;
                for (String v : lineValues) {

                    Cell cell = row.createCell(j);
                    cell.setCellValue(new CellValue(v));

                    cells[i][j] = v;
                    j++;
                }
                i++;
            }
        }

    }

    public String[][] getCells() {
        return cells;
    }

    public Sheet getSheet() {
        return sheet;
    }
}
