package home.stetsenko.model.cell;

import home.stetsenko.model.row.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CellImpl implements Cell {

    private static final Logger LOGGER = LoggerFactory.getLogger(CellImpl.class);

    private Row row;
    private CellValue cellValue;
    private CellType cellType;
    private CellReference cellReference;

    public CellImpl(Row row) {
        this.row = row;
    }

    @Override
    public Row getRow() {
        return row;
    }

    @Override
    public CellReference getCellReference() {
        return this.cellReference;
    }

    @Override
    public void setCellReference(CellReference cellReference) {
        this.cellReference = cellReference;
    }

    @Override
    public void setCellType(CellType cellType) {
        this.cellType = cellType;
    }

    @Override
    public CellType getCellType() {
        return this.cellType;
    }

    @Override
    public void setCellValue(CellValue cellValue) {
        this.cellValue = cellValue;
    }

    @Override
    public CellValue getCellValue() {
        return this.cellValue;
    }

    @Override
    public String getCellRepresentation() {
        switch (this.cellType) {
            case CELL_TYPE_BLANK:
                return "";
            case CELL_TYPE_ERROR:
                return this.cellValue.getCellErrorValue().getValue();
            case CELL_TYPE_EXPRESSION:
                return this.cellValue.getExpressionValue().getExpression();
            case CELL_TYPE_NUMERIC:
                return String.valueOf(this.cellValue.getNumericValue());
            case CELL_TYPE_STRING:
                return this.cellValue.getTextValue();
        }
        return ExpressionError.UNKNOWN_CELL_TYPE.getValue();
    }

    @Override
    public String toString() {
        return "CellImpl{" +
                "cellValue=" + cellValue +
                ", cellType=" + cellType +
                ", cellReference=" + cellReference +
                '}';
    }
}
