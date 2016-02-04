package home.stetsenko.processing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MultOperationProcessor implements OperationProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(MultOperationProcessor.class);

    @Override
    public int calculate(int leftOperand, int rightOperand) {
        return leftOperand * rightOperand;
    }
}
