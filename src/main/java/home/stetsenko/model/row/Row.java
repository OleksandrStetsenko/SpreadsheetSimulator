package home.stetsenko.model.row;

import home.stetsenko.exceptions.NonExistingReferenceException;
import home.stetsenko.model.cell.Cell;
import home.stetsenko.model.cell.CellType;
import home.stetsenko.model.sheet.Sheet;

import java.util.Iterator;

public interface Row {

    /**
     * Get sheet (parent)
     */
    Sheet getSheet();

    /**
     * Create new cell within the row and return it.
     */
    Cell createCell(int colIndex);

    /**
     * Create new cell within the row and return it.
     */
    Cell createCell(int colIndex, CellType type);

    /**
     * Set the row number of this row.
     * @throws IllegalArgumentException if rowNum < 0
     */
    void setRowIndex(int rowNum);

    /**
     * Get row number this row represents
     */
    int getRowIndex();

    /**
     * Get the cell representing a given column
     */
    Cell getCell(int colIndex) throws NonExistingReferenceException;

    /**
     * Gets the index of the last cell contained in this row <b>PLUS ONE</b>.
     * @return short representing the last logical cell in the row <b>PLUS ONE</b>,
     *   or -1 if the row does not contain any cells.
     */
    short getLastCellNum();

    /**
     * @return Cell iterator of the physically defined cells.  Note element 4 may
     * actually be row cell depending on how many are defined!
     */
    Iterator<Cell> cellIterator();

}
