package home.stetsenko.model.cell;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CellValue {

    private static final Logger LOGGER = LoggerFactory.getLogger(CellValue.class);
    private String textValue;
    private int numericValue;
    private String expressionValue;
    private FormulaError cellErrorValue;

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

    public String getExpressionValue() {
        return expressionValue;
    }

    public CellValue setExpressionValue(String expressionValue) {
        this.expressionValue = expressionValue;
        return this;
    }

    public FormulaError getCellErrorValue() {
        return cellErrorValue;
    }

    public CellValue setCellErrorValue(FormulaError cellErrorValue) {
        this.cellErrorValue = cellErrorValue;
        return this;
    }
}
