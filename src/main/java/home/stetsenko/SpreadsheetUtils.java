package home.stetsenko;

import home.stetsenko.model.cell.Cell;
import home.stetsenko.model.row.Row;
import home.stetsenko.model.sheet.Sheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Locale;

public class SpreadsheetUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpreadsheetUtils.class);

    /**
     * Print array to standard output
     * @param cells cells to be printed
     * @throws IllegalArgumentException when array is null or empty
     */
    public static void printArray(String[][] cells) {

        if (cells == null || cells.length == 0) {
            throw new IllegalArgumentException("Array should be not null or not empty!");
        }

        int cellSize = 18;
        int rowLength = cells[0].length * cellSize + cells[0].length + 1;
        final char[] array = new char[rowLength];
        Arrays.fill(array, '-');
        String rowDivider = new String(array);
        for(int i = 0; i < cells.length; i++)
        {
            System.out.println(rowDivider);
            for(int j = 0; j < cells[i].length; j++)
            {
                System.out.printf("|%"+cellSize+"s",cells[i][j]);
                if(j == (cells[i].length - 1)) System.out.println("|");
            }
        }
        System.out.println(rowDivider);
    }

    /**
     * Print sheet to standard output
     * @param sheet sheet to be printed
     */
    public static void printSheet(Sheet sheet) {

        int cellSize = 18;
        //todo refactor this
        int rowLength = sheet.getRow(0).getLastCellNum() * cellSize + sheet.getRow(0).getLastCellNum() + 1;
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
                String textValue = cell.getCellValue().getTextValue();
                System.out.printf("|%" + cellSize + "s", textValue);
                if( !cellIterator.hasNext() ) {
                    System.out.println("|");
                }
            }
        }
        System.out.println(rowDivider);
    }

}
