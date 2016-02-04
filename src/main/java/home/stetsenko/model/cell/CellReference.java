package home.stetsenko.model.cell;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CellReference {

    private static Logger LOGGER = LoggerFactory.getLogger(CellReference.class);

    /**
     * Matches a run of one letter followed by a run of one digit.
     * The run of letters is group 1 and the run of digits is group 2.
     */
    private static final Pattern CELL_REF_PATTERN = Pattern.compile("([A-Za-z]{1})([0-9]{1})");

    private int rowIndex = 0;
    private int colIndex = 0;

    /**
     * Indexes should start from 0
     * @param rowIndex
     * @param colIndex
     */
    public CellReference(int rowIndex, int colIndex) {
        this.rowIndex = rowIndex;
        this.colIndex = colIndex;
    }

    public CellReference(String str) {

        if (str.length() < 2) {
            //todo: exception in constructor
            throw new IllegalArgumentException("Empty string not allowed");
        }

        Matcher cellRefPatternMatcher = CELL_REF_PATTERN.matcher(str);
        if ( !cellRefPatternMatcher.matches() ) {
            //todo: exception in constructor
            throw new IllegalArgumentException("String representation of cell is invalid");
        }

        String lettersGroup = cellRefPatternMatcher.group(1);
        String digitsGroup = cellRefPatternMatcher.group(2);

        this.rowIndex = Integer.parseInt(digitsGroup) - 1;
        this.colIndex = convertColStringToIndex(lettersGroup);

    }

    /**
     * Index starts form 0
     * @return row index
     */
    public int getRowIndex() {
        return rowIndex;
    }

    /**
     * Index starts from 0
     * @return
     */
    public int getColIndex() {
        return colIndex;
    }

    /**
     * Set row index
     */
    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    /**
     * Set col index
     */
    public void setColIndex(int colIndex) {
        this.colIndex = colIndex;
    }

    /**
     * 'A' -> 0
     * 'Z' -> 25
     * @return zero based column index
     */
    public static int convertColStringToIndex(String ref) {
        int retval = 0;
        char[] refs = ref.toUpperCase(Locale.ROOT).toCharArray();
        for (int k = 0; k < refs.length; k++) {
            char thechar = refs[k];
            // Character is uppercase letter, find relative value to A
            retval = (retval * 26) + (thechar - 'A' + 1);
        }
        return retval-1;
    }

    /**
     * 3 -> 'D'
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
