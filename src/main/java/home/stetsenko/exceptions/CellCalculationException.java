package home.stetsenko.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CellCalculationException extends Exception {

    private static final Logger LOGGER = LoggerFactory.getLogger(CellCalculationException.class);

    public CellCalculationException(String message) {
        super(message);
    }

    public CellCalculationException(String message, Throwable cause) {
        super(message, cause);
    }

    public CellCalculationException(Throwable cause) {
        super(cause);
    }
}
