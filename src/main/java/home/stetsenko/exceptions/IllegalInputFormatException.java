package home.stetsenko.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IllegalInputFormatException extends Exception {

    private static final Logger LOGGER = LoggerFactory.getLogger(IllegalInputFormatException.class);

    public IllegalInputFormatException(String message) {
        super(message);
    }

    public IllegalInputFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalInputFormatException(Throwable cause) {
        super(cause);
    }
}
