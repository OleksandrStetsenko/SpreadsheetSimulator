package home.stetsenko;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
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
     * takes in a column reference portion of a CellRef and converts it from
     * ALPHA-26 number format to 0-based base 10.
     * 'A' -> 0
     * 'Z' -> 25
     * 'AA' -> 26
     * 'IV' -> 255
     * @return zero based column index
     */
    public static int convertColStringToIndex(String ref) {
        int retval=0;
        char[] refs = ref.toUpperCase(Locale.ROOT).toCharArray();
        for (int k=0; k<refs.length; k++) {
            char thechar = refs[k];
            // Character is uppercase letter, find relative value to A
            retval = (retval * 26) + (thechar - 'A' + 1);
        }
        return retval-1;
    }

    /**
     * Takes in a 0-based base-10 column and returns a ALPHA-26
     *  representation.
     * eg column #3 -> D
     */
    public static String convertNumToColString(int col) {
        // Excel counts column A as the 1st column, we
        //  treat it as the 0th one
        int excelColNum = col + 1;

        StringBuilder colRef = new StringBuilder(2);
        int colRemain = excelColNum;

        while(colRemain > 0) {
            int thisPart = colRemain % 26;
            if(thisPart == 0) { thisPart = 26; }
            colRemain = (colRemain - thisPart) / 26;

            // The letter A is at 65
            char colChar = (char)(thisPart+64);
            colRef.insert(0, colChar);
        }

        return colRef.toString();
    }
}
