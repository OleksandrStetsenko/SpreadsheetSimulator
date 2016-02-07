package home.stetsenko.model.sheet;

import home.stetsenko.exceptions.NonExistingReferenceException;
import home.stetsenko.model.row.Row;

import java.util.Iterator;

public interface Sheet {

    /**
     * Create a new row within the sheet and return it
     */
    Row createRow(int rowIndex);

    /**
     * Returns the row
     */
    Row getRow(int rowIndex) throws NonExistingReferenceException;

    /**
     * Returns number of rows
     */
    int getNumberOfRows();

    /**
     *  Returns an iterator of the rows
     */
    Iterator<Row> rowIterator();

}
