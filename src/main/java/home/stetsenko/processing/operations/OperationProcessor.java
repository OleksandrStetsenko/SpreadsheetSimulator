package home.stetsenko.processing.operations;

import home.stetsenko.exceptions.CellCalculationException;

public interface OperationProcessor {

    int calculate(int leftOperand, int rightOperand) throws CellCalculationException;

}
