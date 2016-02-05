package home.stetsenko.model.cell;

import home.stetsenko.model.row.Row;

public interface Cell {

    boolean isCalculated();

    void setCalculated(boolean isCalculated);

    /**
     * Get parent (row)
     */
    Row getRow();

    /**
     * @return cell reference
     */
    CellReference getCellReference();

    /**
     * Set sell reference
     */
    void setCellReference(CellReference cellReference);

    /**
     * Set the cells type (numeric, formula or string).
     */
    void setCellType(CellType cellType);

    /**
     * Return the cell type.
     */
    CellType getCellType();

    /**
     * Set a int value for the cell
     */
    void setCellValue(CellValue value);

    /**
     * Get a int value for the cell
     */
    CellValue getCellValue();

    /**
     * Returns string representation of cell according to cell type
     * @see home.stetsenko.model.cell.CellType
     */
    String getCellRepresentation();

}
