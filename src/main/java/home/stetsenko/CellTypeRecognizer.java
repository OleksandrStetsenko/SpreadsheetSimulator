package home.stetsenko;

import home.stetsenko.model.cell.CellType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CellTypeRecognizer {

    private static final Logger LOGGER = LoggerFactory.getLogger(CellTypeRecognizer.class);

    private static final Pattern STRING_CELL_TYPE_PATTERN = Pattern.compile("^'(.*)");
    private static final Pattern NUMERIC_CELL_TYPE_PATTERN = Pattern.compile("^(\\d+)"); //starts from ^ and repeat numeric one or more times
    private static final Pattern EXPRESSION_CELL_TYPE_PATTERN = Pattern.compile("^=(([A-Za-z]{1})([0-9]{1})|([0-9]+))(([\\+\\-\\*\\/](([A-Za-z]{1})([0-9]{1})|([0-9]+)))+)?$");
    private static final Pattern TERM_PATTERN = Pattern.compile("([A-Za-z]{1})([0-9]{1})|([0-9]+)");
    private static final Pattern OPERATIONPATTERN = Pattern.compile("[\\+\\-\\*\\/]");

    public static CellType recognizeType(String str) {

        //if string is empty - this cell is blank type
        if (str.length() == 0) {
            return CellType.CELL_TYPE_BLANK;
        }

        //check that it is string type
        Matcher stringPatternMatcher = STRING_CELL_TYPE_PATTERN.matcher(str);
        if(stringPatternMatcher.matches()) {
            return CellType.CELL_TYPE_STRING;
        }

        //check that it is numeric type
        Matcher numericPatternMatcher = NUMERIC_CELL_TYPE_PATTERN.matcher(str);
        if(numericPatternMatcher.matches()) {
            return CellType.CELL_TYPE_NUMERIC;
        }

        //check that it is expression type
        Matcher expressionPatternMatcher = EXPRESSION_CELL_TYPE_PATTERN.matcher(str);
        if(expressionPatternMatcher.matches()) {
            return CellType.CELL_TYPE_EXPRESSION;
        }

        return CellType.CELL_TYPE_ERROR;
    }

    public static String extractText(String str) {
        Matcher stringPatternMatcher = STRING_CELL_TYPE_PATTERN.matcher(str);
        if(stringPatternMatcher.matches()) {
            return stringPatternMatcher.group(1);
        } else {
            throw new IllegalArgumentException("String does not satisfy string cell type pattern");
        }
    }

    public static int extractNumeric(String str) {
        Matcher numericPatternMatcher = NUMERIC_CELL_TYPE_PATTERN.matcher(str);
        if(numericPatternMatcher.matches()) {
            return Integer.parseInt(numericPatternMatcher.group(1));
        } else {
            throw new IllegalArgumentException("String does not satisfy numeric cell type pattern");
        }
    }

    //todo should return expression
    public static void extractExpression(String str) {

        LOGGER.debug("Input string = {}", str);

        List<String> termList = new ArrayList();

        //todo: IDEA: use pattern to find separately operators and terms

        String[] strings = TERM_PATTERN.split(str);
        for (int i = 0; i < strings.length; i++) {
            LOGGER.debug(strings[i]);
        }

        //if(termPatternMatcher.matches()) {
//            for (int i = 1; i < termPatternMatcher.groupCount() + 1; i++) {
//                String term = termPatternMatcher.group(i);
//                LOGGER.debug("TERM = {}", term);
//            }
        //} else {
        //    throw new IllegalArgumentException("String does not satisfy expression cell type pattern");
        //}

    }

}
