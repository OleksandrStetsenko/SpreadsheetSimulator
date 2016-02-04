package home.stetsenko.model.cell;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class ExpressionValue {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExpressionValue.class);
    //todo: move to constants
    public static final Pattern TERM_PATTERN = Pattern.compile("([A-Za-z]{1})([0-9]{1})|([0-9]+)");
    private static final Pattern OPERATION_PATTERN = Pattern.compile("[\\=\\+\\-\\*\\/]");
    private String expression;
    private List<String> termList;
    private List<String> operationList;

    public ExpressionValue(String expression) {
        this.expression = expression;
        extractExpressionTerms();
        extractExpressionOperations();
    }

    public List<String> getTermList() {
        return termList;
    }

    public List<String> getOperationList() {
        return operationList;
    }

    public String getExpression() {
        return expression;
    }

    private void extractExpressionTerms() {
        LOGGER.debug("Input string = {}", expression);
        this.termList = new ArrayList<String>();
        String[] strings = OPERATION_PATTERN.split(expression);
        for (int i = 1; i < strings.length; i++) {
            termList.add(strings[i]);
        }
        LOGGER.debug("Terms from string = {}", termList);
    }

    private void extractExpressionOperations() {
        LOGGER.debug("Input string = {}", expression);
        this.operationList = new ArrayList<String>();

        String[] strings = TERM_PATTERN.split(expression);
        for (int i = 1; i < strings.length; i++) {
            operationList.add(strings[i]);
        }
        LOGGER.debug("Operations from string = {}", operationList);
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
