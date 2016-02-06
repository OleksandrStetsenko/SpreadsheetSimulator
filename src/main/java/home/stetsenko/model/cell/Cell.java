package home.stetsenko.model.cell;

import home.stetsenko.model.row.Row;

public interface Cell {

    /**
     * Check that sell has been calculated
     * @return true if cell has been calculated
     */
    boolean isCalculated();

    /**
     * Set cell calculated
     * @param isCalculated
     */
    void setCalculated(boolean isCalculated);

    /**
     * Get parent (row)
     */
    Row getRow();

    /**
     * Returns reference of this cell
     * @see CellReference
     */
    CellReference getCellReference();

    /**
     * Set sell reference
     * @see CellReference
     */
    void setCellReference(CellReference cellReference);

    /**
     * Set the cells type (numeric, formula or string).
     * @see CellType
     */
    void setCellType(CellType cellType);

    /**
     * Return the cell type.
     * @see CellType
     */
    CellType getCellType();

    /**
     * Set a int value for the cell
     */
    void setCellValue(CellValue value);

    /**
     * Get a int value for the cell
     * @see CellValue
     */
    CellValue getCellValue();

    /**
     * Returns string representation of cell according to cell type
     * @see home.stetsenko.model.cell.CellType
     */
    String getCellRepresentation();

}
