package home.stetsenko.model.cell;

public enum CellType {

    CELL_TYPE_NUMERIC(0),
    CELL_TYPE_STRING(1),
    CELL_TYPE_EXPRESSION(2),
    CELL_TYPE_BLANK(3),
    CELL_TYPE_ERROR(4);

    private int value;

    CellType(int value) {
        this.value = value;
    }
}
