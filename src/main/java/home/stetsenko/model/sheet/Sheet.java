package home.stetsenko.model.sheet;

import home.stetsenko.model.row.Row;

public interface Sheet {

    /**
     * Create a new row within the sheet and return it
     */
    Row createRow(int rowIndex);

    /**
     * Returns the row
     */
    Row getRow(int rowIndex);

}
