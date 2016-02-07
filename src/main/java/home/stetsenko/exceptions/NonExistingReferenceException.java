package home.stetsenko.exceptions;

import home.stetsenko.model.cell.ExpressionError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NonExistingReferenceException extends Exception {

    private static final Logger LOGGER = LoggerFactory.getLogger(NonExistingReferenceException.class);
    private ExpressionError expressionError;

    public NonExistingReferenceException(ExpressionError expressionError, String message) {
        super(message);
        this.expressionError = expressionError;
    }

    public NonExistingReferenceException(ExpressionError expressionError, String message, Throwable cause) {
        super(message, cause);
        this.expressionError = expressionError;
    }

    public NonExistingReferenceException(ExpressionError expressionError, Throwable cause) {
        super(cause);
        this.expressionError = expressionError;
    }

    public ExpressionError getExpressionError() {
        return expressionError;
    }
}
