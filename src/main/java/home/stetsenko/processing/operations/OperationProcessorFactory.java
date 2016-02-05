package home.stetsenko.processing.operations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OperationProcessorFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(OperationProcessorFactory.class);

    public OperationProcessor getOperationProcessor(String operator) {

        if ("+".equals(operator)) {
            LOGGER.debug("AddOperationProcessor is created");
            return new AddOperationProcessor();
        }

        if ("/".equals(operator)) {
            LOGGER.debug("DivOperationProcessor is created");
            return new DivOperationProcessor();
        }

        if ("*".equals(operator)) {
            LOGGER.debug("MultOperationProcessor is created");
            return new MultOperationProcessor();
        }

        if ("-".equals(operator)) {
            LOGGER.debug("SubOperationProcessor is created");
            return new SubOperationProcessor();
        }

        return null;
    }

}
