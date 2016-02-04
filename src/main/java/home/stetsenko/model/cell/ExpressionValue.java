package home.stetsenko.model.cell;

import home.stetsenko.CellTypeRecognizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class ExpressionValue {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExpressionValue.class);
    private static final Pattern TERM_PATTERN = Pattern.compile("([A-Za-z]{1})([0-9]{1})|([0-9]+)");
    private static final Pattern OPERATION_PATTERN = Pattern.compile("[\\=\\+\\-\\*\\/]");
    private String expression;
    private List<String> termList;
    private List<String> operationList;

    public ExpressionValue(String expression) {
        this.expression = expression;
        this.termList = extractExpressionTerms(expression);
        this.operationList = extractExpressionOperations(expression);
    }

    public List<String> getTermList() {
        return termList;
    }

    public List<String> getOperationList() {
        return operationList;
    }

    public static List<String> extractExpressionTerms(String str) {
        LOGGER.debug("Input string = {}", str);
        List<String> termList = new ArrayList<String>();
        String[] strings = OPERATION_PATTERN.split(str);
        for (int i = 1; i < strings.length; i++) {
            termList.add(strings[i]);
        }
        LOGGER.debug("Terms from string = {}", termList);
        return termList;
    }

    public static List<String> extractExpressionOperations(String str) {
        LOGGER.debug("Input string = {}", str);
        List<String> operationList = new ArrayList<String>();

        String[] strings = TERM_PATTERN.split(str);
        for (int i = 1; i < strings.length; i++) {
            operationList.add(strings[i]);
        }
        LOGGER.debug("Operations from string = {}", operationList);
        return operationList;
    }

    @Override
    public String toString() {
        return "ExpressionValue{" +
                "expression='" + expression + '\'' +
                ", termList=" + termList +
                ", operationList=" + operationList +
                '}';
    }
}
