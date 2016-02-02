package home.stetsenko.model.cell;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CellValue {

    private static final Logger LOGGER = LoggerFactory.getLogger(CellValue.class);
    private CellType cellType;
    private String textValue;
    private int numericValue;

    private CellValue(CellType cellType, String textValue, int numericValue) {
        this.textValue = textValue;
        this.numericValue = numericValue;
        this.cellType = cellType;
    }

    public CellValue(String textValue) {
        this.cellType = CellType.CELL_TYPE_STRING;
        this.textValue = textValue;
    }

    public CellValue(int numericValue) {
        this.cellType = CellType.CELL_TYPE_NUMERIC;
        this.numericValue = numericValue;
    }

    public String getTextValue() {
        return textValue;
    }

    public void setTextValue(String textValue) {
        this.textValue = textValue;
    }

    public int getNumericValue() {
        return numericValue;
    }

    public void setNumericValue(int numericValue) {
        this.numericValue = numericValue;
    }

    public CellType getCellType() {
        return cellType;
    }

    public void setCellType(CellType cellType) {
        this.cellType = cellType;
    }

    @Override
    public String toString() {
        return "CellValue{" +
                "cellType=" + cellType +
                ", textValue='" + textValue + '\'' +
                ", numericValue=" + numericValue +
                '}';
    }
}
