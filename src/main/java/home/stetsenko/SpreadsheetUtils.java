package home.stetsenko;

import home.stetsenko.exceptions.NonExistingReferenceException;
import home.stetsenko.model.cell.Cell;
import home.stetsenko.model.row.Row;
import home.stetsenko.model.sheet.Sheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Iterator;

public class SpreadsheetUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpreadsheetUtils.class);

    /**
     * Print sheet to standard output
     *
     * @param sheet sheet to be printed
     */
    public static void printSheet(Sheet sheet) {

        try {
            int cellSize = 18;
            if (sheet != null && sheet.getNumberOfRows() != 0) {
                int rowLength = 0;

                rowLength = sheet.getRow(0).getLastCellNum() * cellSize + sheet.getRow(0).getLastCellNum() + 1;

                final char[] array = new char[rowLength];
                Arrays.fill(array, '-');
                String rowDivider = new String(array);
                Iterator<Row> rowIterator = sheet.rowIterator();
                while (rowIterator.hasNext()) {
                    System.out.println(rowDivider);
                    Row row = rowIterator.next();
                    Iterator<Cell> cellIterator = row.cellIterator();
                    while (cellIterator.hasNext()) {
                        Cell cell = cellIterator.next();
                        System.out.printf("|%" + cellSize + "s", cell.getCellRepresentation());
                        if (!cellIterator.hasNext()) {
                            System.out.println("|");
                        }
                    }
                }
                System.out.println(rowDivider);
            } else {
                LOGGER.error(SpreadsheetConstants.MESSAGE_SHEET_CAN_BE_PRINTED);
            }
        } catch (NonExistingReferenceException e) {
            LOGGER.error(SpreadsheetConstants.MESSAGE_NON_EXISTING_CELL_REF);
        }
    }

}
