package home.stetsenko.model.cell;

import home.stetsenko.SpreadsheetConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;

public class Term {

    private static final Logger LOGGER = LoggerFactory.getLogger(Term.class);
    private TermType termType;
    private int numericValue;
    private CellReference cellReferenceValue;

    public Term(String termString) {
        LOGGER.debug("Input string = {}", termString);
        Matcher stringPatternMatcher = SpreadsheetConstants.PATTERN_TERM.matcher(termString);
        if(stringPatternMatcher.matches()) {
            String cellRefGroup = stringPatternMatcher.group(1);
            String numericGroup = stringPatternMatcher.group(4);
            if (cellRefGroup != null) {
                this.termType = TermType.TERM_TYPE_CELL_REFERENCE;
            } else if (numericGroup != null) {
                this.termType = TermType.TERM_TYPE_NUMERIC;
            } else {
                this.termType = TermType.TERM_TYPE_ERROR;
            }

            setTermValue(cellRefGroup, numericGroup);

        } else {
            throw new IllegalArgumentException(SpreadsheetConstants.MESSAGE_STRING_DOES_NOT_SATISFY_CELL_TYPE_PATTERN);
        }
    }

    private void setTermValue(String cellRefGroup, String numericGroup) {
        if (termType == TermType.TERM_TYPE_CELL_REFERENCE) {
            this.cellReferenceValue = new CellReference(cellRefGroup);
        } else if (termType == TermType.TERM_TYPE_NUMERIC) {
            this.numericValue = Integer.parseInt(numericGroup);
        }
    }

    public TermType getTermType() {
        return termType;
    }

    public int getNumericValue() {
        return numericValue;
    }

    public CellReference getCellReferenceValue() {
        return cellReferenceValue;
    }

    @Override
    public String toString() {
        return "Term{" +
                "termType=" + termType +
                ", numericValue=" + numericValue +
                ", cellReferenceValue=" + cellReferenceValue +
                '}';
    }
}
