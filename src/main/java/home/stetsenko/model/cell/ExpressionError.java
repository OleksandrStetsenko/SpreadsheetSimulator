package home.stetsenko.model.cell;

public enum ExpressionError {

    /**
     * When cell reference is invalid or operation is not supported
     */
    REF("#REF!"),

    /**
     * When circular reference detected
     */
    CIRCULAR_REF("#CIRCULAR_REF!"),

    /**
     * Unknown cell type detected
     */
    UNKNOWN_CELL_TYPE("#UNKNOWN_CELL_TYPE!"),

    /**
     * Div 0
     */
    DIV_0("#DIV/0!"),

    /**
     * Non existing reference
     */
    NON_EXIST_REF("#NON_EXIST_REF!");

    private String value;

    ExpressionError(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
