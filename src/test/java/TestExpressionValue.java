import home.stetsenko.model.cell.ExpressionValue;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.junit.Assert.assertArrayEquals;

public class TestExpressionValue {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestExpressionValue.class);

    @Test
    public void test_expressionValue() {
        ExpressionValue expressionValue = new ExpressionValue("=A1+B3*5/C4-R4");

        List<String> terms = expressionValue.getTermList();
        String[] arrayActualTerms = terms.toArray(new String[terms.size()]);
        String[] arrayExpectedTerms = new String[] {"A1", "B3", "5", "C4", "R4"};
        assertArrayEquals(arrayExpectedTerms, arrayActualTerms);

        List<String> operations = expressionValue.getOperationList();
        String[] arrayActualOperations = operations.toArray(new String[operations.size()]);
        String[] arrayExpectedOperations = new String[] {"+", "*", "/", "-"};
        assertArrayEquals(arrayExpectedOperations, arrayActualOperations);
    }

}
