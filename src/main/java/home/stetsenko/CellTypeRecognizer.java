package home.stetsenko;

import home.stetsenko.model.cell.CellType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CellTypeRecognizer {

    private static final Logger LOGGER = LoggerFactory.getLogger(CellTypeRecognizer.class);

    private static final Pattern STRING_CELL_TYPE_PATTERN = Pattern.compile("^'(.*)");
    private static final Pattern NUMERIC_CELL_TYPE_PATTERN = Pattern.compile("^\\d+"); //starts from ^ and repeat numeric one or more times

    //todo
    private static final Pattern EXPRESSION_CELL_TYPE_PATTERN = Pattern.compile("^=(([A-Za-z]{1})([0-9]{1})|([0-9]*))");

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

}
