package home.stetsenko.model.cell;

public interface Cell {

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
    void setCellValue(int value);

    /**
     * Get a int value for the cell
     */
    int getCellValue();

    /**
     * Set a error value for the cell
     */
    void setCellErrorValue(FormulaError value);

    /**
     * Get a error value for the cell
     */
    FormulaError getCellErrorValue();

}
