package home.stetsenko;

import home.stetsenko.model.cell.CellType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CellTypeRecognizer {

    private static final Logger LOGGER = LoggerFactory.getLogger(CellTypeRecognizer.class);

    private static final Pattern STRING_CELL_TYPE_PATTERN = Pattern.compile("^'(.*)");
    private static final Pattern NUMERIC_CELL_TYPE_PATTERN = Pattern.compile("^(\\d+)"); //repeat numeric one or more times
    private static final Pattern EXPRESSION_CELL_TYPE_PATTERN = Pattern.compile("^=(([A-Za-z]{1})([0-9]{1})|([0-9]+))(([\\+\\-\\*\\/](([A-Za-z]{1})([0-9]{1})|([0-9]+)))+)?$");

    public static CellType recognizeType(String str) {

        LOGGER.debug("Input string = {}", str);

        //if string is empty - this cell is blank type
        if (str.length() == 0) {
            LOGGER.debug("Cell type: blank");
            return CellType.CELL_TYPE_BLANK;
        }

        //check that it is string type
        Matcher stringPatternMatcher = STRING_CELL_TYPE_PATTERN.matcher(str);
        if(stringPatternMatcher.matches()) {
            LOGGER.debug("Cell type: string");
            return CellType.CELL_TYPE_STRING;
        }

        //check that it is numeric type
        Matcher numericPatternMatcher = NUMERIC_CELL_TYPE_PATTERN.matcher(str);
        if(numericPatternMatcher.matches()) {
            LOGGER.debug("Cell type: numeric");
            return CellType.CELL_TYPE_NUMERIC;
        }

        //check that it is expression type
        Matcher expressionPatternMatcher = EXPRESSION_CELL_TYPE_PATTERN.matcher(str);
        if(expressionPatternMatcher.matches()) {
            LOGGER.debug("Cell type: expression");
            return CellType.CELL_TYPE_EXPRESSION;
        }

        LOGGER.debug("Cell type: error");
        return CellType.CELL_TYPE_ERROR;
    }

    public static String extractText(String str) {

        LOGGER.debug("Input string = {}", str);

        Matcher stringPatternMatcher = STRING_CELL_TYPE_PATTERN.matcher(str);
        if(stringPatternMatcher.matches()) {
            String s = stringPatternMatcher.group(1);
            LOGGER.debug("Result test = {}", s);
            return s;
        } else {
            LOGGER.error(SpreadsheetConstants.MESSAGE_STRING_DOES_NOT_SATISFY_CELL_TYPE_PATTERN);
            throw new IllegalArgumentException(SpreadsheetConstants.MESSAGE_STRING_DOES_NOT_SATISFY_CELL_TYPE_PATTERN);
        }
    }

    public static int extractNumeric(String str) {

        LOGGER.debug("Input string = {}", str);

        Matcher numericPatternMatcher = NUMERIC_CELL_TYPE_PATTERN.matcher(str);
        if(numericPatternMatcher.matches()) {
            return Integer.parseInt(numericPatternMatcher.group(1));
        } else {
            LOGGER.error(SpreadsheetConstants.MESSAGE_STRING_DOES_NOT_SATISFY_CELL_TYPE_PATTERN);
            throw new IllegalArgumentException(SpreadsheetConstants.MESSAGE_STRING_DOES_NOT_SATISFY_CELL_TYPE_PATTERN);
        }
    }

}
