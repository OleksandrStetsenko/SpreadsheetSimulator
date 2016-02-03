import home.stetsenko.CellTypeRecognizer;
import home.stetsenko.model.cell.CellType;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertTrue;

public class CellTypeTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(CellTypeTest.class);

    @Test
    public void test_recognizeType() {

        assertTrue(CellType.CELL_TYPE_BLANK.equals(CellTypeRecognizer.recognizeType("")));

        assertTrue(CellType.CELL_TYPE_STRING.equals(CellTypeRecognizer.recognizeType("'Sample")));
        assertTrue(CellType.CELL_TYPE_STRING.equals(CellTypeRecognizer.recognizeType("'Sam'ple ")));

        assertTrue(CellType.CELL_TYPE_NUMERIC.equals(CellTypeRecognizer.recognizeType("4")));
        assertTrue(CellType.CELL_TYPE_NUMERIC.equals(CellTypeRecognizer.recognizeType("55")));

        assertTrue(CellType.CELL_TYPE_EXPRESSION.equals(CellTypeRecognizer.recognizeType("=5")));
        assertTrue(CellType.CELL_TYPE_EXPRESSION.equals(CellTypeRecognizer.recognizeType("=A1")));

        assertTrue(CellType.CELL_TYPE_ERROR.equals(CellTypeRecognizer.recognizeType("#")));
    }

}
