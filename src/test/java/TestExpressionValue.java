import home.stetsenko.model.cell.CellReference;
import home.stetsenko.model.cell.ExpressionValue;
import home.stetsenko.model.cell.Term;
import home.stetsenko.model.cell.TermType;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

public class TestExpressionValue {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestExpressionValue.class);

    @Test
    public void test_expressionValue() {
        ExpressionValue expressionValue = new ExpressionValue("=A1+B3*5/C4-R4");

        List<Term> terms = expressionValue.getTermList();
        Term termA1 = terms.get(0);
        TermType termType = termA1.getTermType();
        assertTrue(termType == TermType.TERM_TYPE_CELL_REFERENCE);
        CellReference cellReferenceValue = termA1.getCellReferenceValue();
        assertTrue(cellReferenceValue.getColIndex() == 0); //A1
        assertTrue(cellReferenceValue.getRowIndex() == 0); //A1

        Term term5 = terms.get(2);
        TermType term5Type = term5.getTermType();
        assertTrue(term5Type == TermType.TERM_TYPE_NUMERIC);
        assertTrue(term5.getNumericValue() == 5);

        List<String> operations = expressionValue.getOperationList();
        String[] arrayActualOperations = operations.toArray(new String[operations.size()]);
        String[] arrayExpectedOperations = new String[] {"+", "*", "/", "-"};
        assertArrayEquals(arrayExpectedOperations, arrayActualOperations);
    }

}
