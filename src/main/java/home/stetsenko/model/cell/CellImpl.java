package home.stetsenko.model.cell;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CellImpl implements Cell {

    private static final Logger LOGGER = LoggerFactory.getLogger(CellImpl.class);

    private CellValue cellValue;
    private CellType cellType;
    private CellReference cellReference;
    private FormulaError cellErrorValue;

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
    public void setCellErrorValue(FormulaError cellErrorValue) {
        this.cellErrorValue = cellErrorValue;
    }

    @Override
    public FormulaError getCellErrorValue() {
        return this.cellErrorValue;
    }

    @Override
    public String toString() {
        return "CellImpl{" +
                "cellValue=" + cellValue +
                ", cellType=" + cellType +
                ", cellReference=" + cellReference +
                ", cellErrorValue=" + cellErrorValue +
                '}';
    }
}
