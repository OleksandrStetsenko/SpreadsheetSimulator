import home.stetsenko.SpreadsheetUtils;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReferenceConverterTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReferenceConverterTest.class);

    @Test
    public void test_convertColStringToIndex() {
        Assert.assertTrue(SpreadsheetUtils.convertColStringToIndex("A") == 0);
        Assert.assertTrue(SpreadsheetUtils.convertColStringToIndex("a") == 0);
        Assert.assertTrue(SpreadsheetUtils.convertColStringToIndex("B") == 1);
        Assert.assertTrue(SpreadsheetUtils.convertColStringToIndex("b") == 1);
        Assert.assertTrue(SpreadsheetUtils.convertColStringToIndex("C") == 2);
        Assert.assertTrue(SpreadsheetUtils.convertColStringToIndex("c") == 2);
        Assert.assertTrue(SpreadsheetUtils.convertColStringToIndex("Z") == 25);
        Assert.assertTrue(SpreadsheetUtils.convertColStringToIndex("z") == 25);
    }

    @Test
    public void test_convertNumToColString() {
        Assert.assertTrue("A".equals(SpreadsheetUtils.convertNumToColString(0)));
        Assert.assertTrue("B".equals(SpreadsheetUtils.convertNumToColString(1)));
        Assert.assertTrue("C".equals(SpreadsheetUtils.convertNumToColString(2)));
        Assert.assertTrue("Z".equals(SpreadsheetUtils.convertNumToColString(25)));
    }

}
