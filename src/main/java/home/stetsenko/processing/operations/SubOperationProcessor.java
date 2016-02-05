package home.stetsenko.processing.operations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SubOperationProcessor implements OperationProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(SubOperationProcessor.class);

    @Override
    public int calculate(int leftOperand, int rightOperand) {
        return leftOperand - rightOperand;
    }
}
