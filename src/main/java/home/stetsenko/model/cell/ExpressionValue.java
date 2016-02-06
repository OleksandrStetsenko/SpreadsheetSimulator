package home.stetsenko.model.cell;

import home.stetsenko.SpreadsheetConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class ExpressionValue {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExpressionValue.class);
    private String expression;
    private List<Term> termList;
    private List<String> operationList;

    public ExpressionValue(String expression) {
        this.expression = expression;
        extractExpressionTerms();
        extractExpressionOperations();
    }

    public List<Term> getTermList() {
        return termList;
    }

    public List<String> getOperationList() {
        return operationList;
    }

    public String getExpression() {
        return expression;
    }

    private void extractExpressionTerms() {
        LOGGER.debug("Input string for extracting terms = {}", expression);
        this.termList = new ArrayList<Term>();
        String[] strings = SpreadsheetConstants.PATTERN_OPERATION.split(expression);
        for (int i = 1; i < strings.length; i++) {
            termList.add(new Term(strings[i]));
        }
        LOGGER.debug("Terms from string = {}", termList);
    }

    private void extractExpressionOperations() {
        LOGGER.debug("Input string for extracting operations = {}", expression);
        this.operationList = new ArrayList<String>();

        String[] strings = SpreadsheetConstants.PATTERN_TERM.split(expression);
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
