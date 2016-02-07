package home.stetsenko.model.row;

import home.stetsenko.exceptions.NonExistingReferenceException;
import home.stetsenko.model.cell.Cell;
import home.stetsenko.model.cell.CellImpl;
import home.stetsenko.model.cell.CellType;
import home.stetsenko.model.cell.ExpressionError;
import home.stetsenko.model.sheet.Sheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.SortedMap;
import java.util.TreeMap;

public class RowImpl implements Row {

    private static final Logger LOGGER = LoggerFactory.getLogger(RowImpl.class);

    private final SortedMap<Integer, Cell> cells = new TreeMap<Integer, Cell>();
    private Sheet sheet;
    private int rowIndex;

    public RowImpl(Sheet sheet, int rowIndex) {
        this.sheet = sheet;
        this.rowIndex = rowIndex;
    }

    @Override
    public Sheet getSheet() {
        return sheet;
    }

    @Override
    public Cell createCell(int colIndex) {
        CellImpl cell = new CellImpl(this);
        //todo: sets blank sell type
        cell.setCellType(CellType.CELL_TYPE_BLANK);
        cells.put(colIndex, cell);
        return cell;
    }

    @Override
    public Cell createCell(int colIndex, CellType type) {
        CellImpl cell = new CellImpl(this);
        cell.setCellType(type);
        cells.put(colIndex, cell);
        return cell;
    }

    @Override
    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    @Override
    public int getRowIndex() {
        return this.rowIndex;
    }

    @Override
    public Cell getCell(int colIndex) throws NonExistingReferenceException {
        if ( ! cells.containsKey(colIndex) ) {
            throw new NonExistingReferenceException(ExpressionError.NON_EXIST_REF, "ref is not existed");
        }
        return cells.get(colIndex);
    }

    @Override
    public short getLastCellNum() {
        return cells.isEmpty() ? -1 : (short)(cells.lastKey() + 1);
    }

    /**
     * returns all cells including empty cells (<code>null</code> values are returned
     * for empty cells).
     * This method is not synchronized. This iterator should not be used after
     * cells are added, moved, or removed, though a ConcurrentModificationException
     * is NOT thrown.
     */
    public class CellIterator implements Iterator<Cell>
    {
        final int maxColumn = getLastCellNum(); //last column PLUS ONE
        int pos = 0;

        @Override
        public boolean hasNext()
        {
            return pos < maxColumn;
        }

        @Override
        public Cell next() throws NoSuchElementException {
            if (hasNext()) {
                return cells.get(pos++);
            } else {
                LOGGER.debug("There are no more cells");
                throw new NoSuchElementException();
            }
        }
        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public Iterator<Cell> cellIterator() {
        return new CellIterator();
    }

    @Override
    public String toString() {
        return "RowImpl{" +
                "cells=" + cells +
                '}';
    }
}
