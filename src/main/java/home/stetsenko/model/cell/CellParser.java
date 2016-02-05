package home.stetsenko.model.cell;

import home.stetsenko.SpreadsheetConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;

public class CellParser {

    private static final Logger LOGGER = LoggerFactory.getLogger(CellParser.class);

    public static CellType recognizeType(String str) {

        LOGGER.debug("Input string = {}", str);

        //if string is empty - this cell is blank type
        if (str.length() == 0) {
            LOGGER.debug("Cell type: blank");
            return CellType.CELL_TYPE_BLANK;
        }

        //check that it is string type
        Matcher stringPatternMatcher = SpreadsheetConstants.PATTERN_STRING_CELL_TYPE.matcher(str);
        if(stringPatternMatcher.matches()) {
            LOGGER.debug("Cell type: string");
            return CellType.CELL_TYPE_STRING;
        }

        //check that it is numeric type
        Matcher numericPatternMatcher = SpreadsheetConstants.PATTERN_NUMERIC_CELL_TYPE.matcher(str);
        if(numericPatternMatcher.matches()) {
            LOGGER.debug("Cell type: numeric");
            return CellType.CELL_TYPE_NUMERIC;
        }

        //check that it is expression type
        Matcher expressionPatternMatcher = SpreadsheetConstants.PATTERN_EXPRESSION_CELL_TYPE.matcher(str);
        if(expressionPatternMatcher.matches()) {
            LOGGER.debug("Cell type: expression");
            return CellType.CELL_TYPE_EXPRESSION;
        }

        LOGGER.debug("Cell type: error");
        return CellType.CELL_TYPE_ERROR;
    }

    public static String extractText(String str) {

        LOGGER.debug("Input string = {}", str);

        Matcher stringPatternMatcher = SpreadsheetConstants.PATTERN_STRING_CELL_TYPE.matcher(str);
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

        Matcher numericPatternMatcher = SpreadsheetConstants.PATTERN_NUMERIC_CELL_TYPE.matcher(str);
        if(numericPatternMatcher.matches()) {
            return Integer.parseInt(numericPatternMatcher.group(1));
        } else {
            LOGGER.error(SpreadsheetConstants.MESSAGE_STRING_DOES_NOT_SATISFY_CELL_TYPE_PATTERN);
            throw new IllegalArgumentException(SpreadsheetConstants.MESSAGE_STRING_DOES_NOT_SATISFY_CELL_TYPE_PATTERN);
        }
    }

}
