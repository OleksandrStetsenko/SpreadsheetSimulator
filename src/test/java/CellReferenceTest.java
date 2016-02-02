import home.stetsenko.model.cell.CellReference;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertTrue;

public class CellReferenceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(CellReferenceTest.class);

    @Test
    public void test_convertColStringToIndex() {
        assertTrue(CellReference.convertColStringToIndex("A") == 0);
        assertTrue(CellReference.convertColStringToIndex("a") == 0);
        assertTrue(CellReference.convertColStringToIndex("B") == 1);
        assertTrue(CellReference.convertColStringToIndex("b") == 1);
        assertTrue(CellReference.convertColStringToIndex("C") == 2);
        assertTrue(CellReference.convertColStringToIndex("c") == 2);
        assertTrue(CellReference.convertColStringToIndex("Z") == 25);
        assertTrue(CellReference.convertColStringToIndex("z") == 25);
    }

    @Test
    public void test_convertNumToColString() {
        assertTrue("A".equals(CellReference.convertNumToColString(0)));
        assertTrue("B".equals(CellReference.convertNumToColString(1)));
        assertTrue("C".equals(CellReference.convertNumToColString(2)));
        assertTrue("Z".equals(CellReference.convertNumToColString(25)));
    }

    @Test
    public void test_createReference() {
        CellReference a1 = new CellReference("A1");
        assertTrue(a1.getRowIndex() == 0);
        assertTrue(a1.getColIndex() == 0);

        CellReference b1 = new CellReference("B2");
        assertTrue(b1.getRowIndex() == 1);
        assertTrue(b1.getColIndex() == 1);
    }

}
