package home.stetsenko;

import java.util.regex.Pattern;

public class SpreadsheetConstants {

    public static final String SEPARATOR = "\\t";

    //messages
    public static final String MESSAGE_STRING_DOES_NOT_SATISFY_CELL_TYPE_PATTERN = "String does not satisfy string cell type pattern";
    public static final String MESSAGE_NO_LENGTH_OR_HEIGHT = "There are no height or length of table";

    //patterns
    public static final Pattern PATTERN_STRING_CELL_TYPE = Pattern.compile("^'(.*)");
    public static final Pattern PATTERN_NUMERIC_CELL_TYPE = Pattern.compile("^(\\d+)"); //repeat numeric one or more times
    public static final Pattern PATTERN_EXPRESSION_CELL_TYPE = Pattern.compile("^=(([A-Za-z]{1})([0-9]{1})|([0-9]+))(([\\+\\-\\*\\/](([A-Za-z]{1})([0-9]{1})|([0-9]+)))+)?$");
    /**
     * Matches a run of one letter followed by a run of one digit.
     * The run of letters is group 1 and the run of digits is group 2.
     */
    public static final Pattern PATTERN_CELL_REF = Pattern.compile("([A-Za-z]{1})([0-9]{1})");
    public static final Pattern PATTERN_OPERATION = Pattern.compile("[\\=\\+\\-\\*\\/]");
    public static final Pattern PATTERN_TERM = Pattern.compile("(([A-Za-z]{1})([0-9]{1}))|([0-9]+)");

}
