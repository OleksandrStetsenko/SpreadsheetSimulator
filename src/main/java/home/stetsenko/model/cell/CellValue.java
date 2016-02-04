package home.stetsenko.model.cell;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CellValue {

    private static final Logger LOGGER = LoggerFactory.getLogger(CellValue.class);
    private String textValue;
    private int numericValue;
    private ExpressionValue expressionValue;
    private ExpressionError cellErrorValue;

    public String getTextValue() {
        return textValue;
    }

    public CellValue setTextValue(String textValue) {
        this.textValue = textValue;
        return this;
    }

    public int getNumericValue() {
        return numericValue;
    }

    public CellValue setNumericValue(int numericValue) {
        this.numericValue = numericValue;
        return this;
    }

    public ExpressionValue getExpressionValue() {
        return expressionValue;
    }

    public CellValue setExpressionValue(ExpressionValue expressionValue) {
        this.expressionValue = expressionValue;
        return this;
    }

    public ExpressionError getCellErrorValue() {
        return cellErrorValue;
    }

    public CellValue setCellErrorValue(ExpressionError cellErrorValue) {
        this.cellErrorValue = cellErrorValue;
        return this;
    }
}
