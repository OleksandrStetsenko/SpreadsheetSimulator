package home.stetsenko.processing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AddOperationProcessor implements OperationProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(AddOperationProcessor.class);

    @Override
    public int calculate(int leftOperand, int rightOperand) {
        return leftOperand + rightOperand;
    }

}
