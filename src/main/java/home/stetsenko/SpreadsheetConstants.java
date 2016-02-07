package home.stetsenko;

import java.util.regex.Pattern;

public class SpreadsheetConstants {

    public static final String SEPARATOR = "\\t";

    //messages
    public static final String MESSAGE_STRING_DOES_NOT_SATISFY_CELL_TYPE_PATTERN = "String does not satisfy string cell type pattern";
    public static final String MESSAGE_FILE_WAS_NOT_FOUND = "File was not found";
    public static final String MESSAGE_SHEET_CAN_BE_PRINTED = "Sheet can be printed, since sheet is null or empty!";
    public static final String MESSAGE_NON_EXISTING_CELL_REF = "Reference to cell is not existed";
    public static final String MESSAGE_NO_LENGTH_OR_HEIGHT = "There are no height or length of table";
    public static final String MESSAGE_WRONG_TYPE_OF_COLNUM_ROWNUM = "Wrong type of input row num or col num";
    public static final String MESSAGE_ACTUAL_ROWS_NOT_EQUAL_EXP = "Actual number of rows is not equal expected";
    public static final String MESSAGE_ACTUAL_COL_NOT_EQUAL_EXP = "Actual number of columns is not equal to expected";

    //patterns
    /**
     * Pattern for recognizing string type of cell and extracting string value from input string
     * Grammar for cell content: <text> = ‘’’{<printable character>}
     */
    public static final Pattern PATTERN_STRING_CELL_TYPE = Pattern.compile("^'(.*)");
    /**
     * Pattern for recognizing numeric type of cell and extracting value from input string
     * Example: 4, 5, 99
     */
    public static final Pattern PATTERN_NUMERIC_CELL_TYPE = Pattern.compile("^(\\d+)"); //repeat numeric one or more times
    /**
     * Pattern for recognizing expression type of cell
     */
    public static final Pattern PATTERN_EXPRESSION_CELL_TYPE = Pattern.compile("^=(([A-Za-z]{1})([0-9]{1})|([0-9]+))(([\\+\\-\\*\\/](([A-Za-z]{1})([0-9]{1})|([0-9]+)))+)?$");
    /**
     * Matches that one letter followed one digit.
     * The run of letters is group 1 and the run of digits is group 2.
     * Grammar:
     * <expression> = ‘=’ <term> { <operation> <term> }
     * <term> = <cell reference> | <non-negative number>
     * <cell reference > = <letter> <digit>
     * <operation> = ‘+’ | ‘-‘ | ‘*’ | ‘/’
     */
    public static final Pattern PATTERN_CELL_REF = Pattern.compile("([A-Za-z]{1})([0-9]{1})");
    /**
     * Possible operations
     * Grammar: <operation> = ‘+’ | ‘-‘ | ‘*’ | ‘/’
     */
    public static final Pattern PATTERN_OPERATION = Pattern.compile("[\\=\\+\\-\\*\\/]");
    /**
     * Pattern for term
     * <term> = <cell reference> | <non-negative number>
     */
    public static final Pattern PATTERN_TERM = Pattern.compile("(([A-Za-z]{1})([0-9]{1}))|([0-9]+)");
}
