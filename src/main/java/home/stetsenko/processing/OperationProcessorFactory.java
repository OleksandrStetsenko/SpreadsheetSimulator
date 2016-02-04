package home.stetsenko.processing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OperationProcessorFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(OperationProcessorFactory.class);

    public OperationProcessor getOperationProcessor(String operator) {

        if ("+".equals(operator)) {
            return new AddOperationProcessor();
        }

        if ("/".equals(operator)) {
            return new DivOperationProcessor();
        }

        if ("*".equals(operator)) {
            return new MultOperationProcessor();
        }

        if ("-".equals(operator)) {
            return new SubOperationProcessor();
        }

        return null;
    }

}
