package home.stetsenko.model.cell;

public enum TermType {

    TERM_TYPE_NUMERIC(0),
    TERM_TYPE_CELL_REFERENCE(1),
    TERM_TYPE_ERROR(2);

    private int value;

    TermType(int value) {
        this.value = value;
    }
}
