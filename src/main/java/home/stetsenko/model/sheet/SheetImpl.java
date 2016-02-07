package home.stetsenko.model.sheet;

import home.stetsenko.exceptions.NonExistingReferenceException;
import home.stetsenko.model.cell.ExpressionError;
import home.stetsenko.model.row.Row;
import home.stetsenko.model.row.RowImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.TreeMap;

public class SheetImpl implements Sheet {

    private static final Logger LOGGER = LoggerFactory.getLogger(SheetImpl.class);
    private final TreeMap<Integer, Row> rows=new TreeMap<Integer, Row>();

    @Override
    public Row createRow(int rowIndex) {
        RowImpl row = new RowImpl(this, rowIndex);
        rows.put(rowIndex, row);
        return row;
    }

    @Override
    public Row getRow(int rowIndex) throws NonExistingReferenceException {
        if ( ! rows.containsKey(rowIndex) ) {
            throw new NonExistingReferenceException(ExpressionError.NON_EXIST_REF, "ref is not existed");
        }
        return rows.get(rowIndex);
    }

    @Override
    public int getNumberOfRows() {
        return rows.size();
    }

    @Override
    public Iterator<Row> rowIterator() {
        @SuppressWarnings("unchecked")
        Iterator<Row> result = (Iterator<Row>)(Iterator<? extends Row>)rows.values().iterator();
        return result;
    }
}
