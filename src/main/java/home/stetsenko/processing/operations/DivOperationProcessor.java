package home.stetsenko.processing.operations;

import home.stetsenko.exceptions.CellCalculationException;
import home.stetsenko.model.cell.ExpressionError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DivOperationProcessor implements OperationProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(DivOperationProcessor.class);

    @Override
    public int calculate(int leftOperand, int rightOperand) throws CellCalculationException {
        if (rightOperand == 0) {
            throw new CellCalculationException(ExpressionError.DIV_0, "Dividing to zero");
        }
        return  leftOperand / rightOperand;
    }
}
