package home.stetsenko.processing.operations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DivOperationProcessor implements OperationProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(DivOperationProcessor.class);

    @Override
    public int calculate(int leftOperand, int rightOperand) {
        return leftOperand / rightOperand;
    }
}
