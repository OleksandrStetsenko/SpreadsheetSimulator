import home.stetsenko.model.cell.CellReference;
import home.stetsenko.model.cell.Term;
import home.stetsenko.model.cell.TermType;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertTrue;

public class TestTerm {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestTerm.class);

    @Test
    public void test_term() {
        Term termA1 = new Term("B2");
        TermType termA1Type = termA1.getTermType();
        assertTrue(TermType.TERM_TYPE_CELL_REFERENCE == termA1Type);
        CellReference cellReferenceValue = termA1.getCellReferenceValue();
        assertTrue(cellReferenceValue.getRowIndex() == 1);
        assertTrue(cellReferenceValue.getColIndex() == 1);

        Term term7 = new Term("7");
        TermType term7Type = term7.getTermType();
        assertTrue(TermType.TERM_TYPE_NUMERIC == term7Type);
        int numericValue = term7.getNumericValue();
        assertTrue(numericValue == 7);
    }

}
