package home.stetsenko.model.sheet;

import home.stetsenko.model.row.Row;
import home.stetsenko.model.row.RowImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.TreeMap;

public class SheetImpl implements Sheet {

    private static final Logger LOGGER = LoggerFactory.getLogger(SheetImpl.class);
    private final TreeMap<Integer, Row> rows=new TreeMap<Integer, Row>();

    @Override
    public Row createRow(int rowIndex) {
        RowImpl row = new RowImpl(rowIndex);
        rows.put(rowIndex, row);
        return row;
    }

    @Override
    public Row getRow(int rowIndex) {
        return rows.get(rowIndex);
    }
}
