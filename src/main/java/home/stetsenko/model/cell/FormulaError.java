package home.stetsenko.model.cell;

public enum FormulaError {

    /**
     * When cell reference is invalid
     */
    REF("#REF!"),

    /**
     * When circular reference detected
     */
    CIRCULAR_REF("#CIRCULAR_REF!"),

    /**
     * When circular reference detected
     */
    UNKNOWN_CELL_TYPE("#UNKNOWN_CELL_TYPE!");

    private String value;

    private FormulaError(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
