import home.stetsenko.model.cell.CellTypeRecognizer;
import home.stetsenko.model.cell.CellType;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertTrue;

public class TestCellParsing {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestCellParsing.class);

    @Test
    public void test_recognizeType() {
        assertTrue(CellType.CELL_TYPE_BLANK.equals(CellTypeRecognizer.recognizeType("")));

        assertTrue(CellType.CELL_TYPE_STRING.equals(CellTypeRecognizer.recognizeType("'Sample")));
        assertTrue(CellType.CELL_TYPE_STRING.equals(CellTypeRecognizer.recognizeType("'Sam'ple ")));

        assertTrue(CellType.CELL_TYPE_NUMERIC.equals(CellTypeRecognizer.recognizeType("4")));
        assertTrue(CellType.CELL_TYPE_NUMERIC.equals(CellTypeRecognizer.recognizeType("55")));

        assertTrue(CellType.CELL_TYPE_EXPRESSION.equals(CellTypeRecognizer.recognizeType("=5")));
        assertTrue(CellType.CELL_TYPE_EXPRESSION.equals(CellTypeRecognizer.recognizeType("=A1")));
        assertTrue(CellType.CELL_TYPE_EXPRESSION.equals(CellTypeRecognizer.recognizeType("=A1+A2")));
        assertTrue(CellType.CELL_TYPE_EXPRESSION.equals(CellTypeRecognizer.recognizeType("=5-3")));
        assertTrue(CellType.CELL_TYPE_EXPRESSION.equals(CellTypeRecognizer.recognizeType("=A1+5")));
        assertTrue(CellType.CELL_TYPE_EXPRESSION.equals(CellTypeRecognizer.recognizeType("=5*A1")));
        assertTrue(CellType.CELL_TYPE_EXPRESSION.equals(CellTypeRecognizer.recognizeType("=5/A1")));
        assertTrue(CellType.CELL_TYPE_EXPRESSION.equals(CellTypeRecognizer.recognizeType("=A1+B3*5/C4-R4")));

        assertTrue(CellType.CELL_TYPE_ERROR.equals(CellTypeRecognizer.recognizeType("#")));
        assertTrue(CellType.CELL_TYPE_ERROR.equals(CellTypeRecognizer.recognizeType("=A1++A2")));
    }

    @Test
    public void test_extractText() {
        assertTrue("Sample".equals(CellTypeRecognizer.extractText("'Sample")));
        assertTrue("Sam'ple".equals(CellTypeRecognizer.extractText("'Sam'ple")));
    }

    @Test
    public void test_extractNumeric() {
        assertTrue(CellTypeRecognizer.extractNumeric("55") == 55);
        assertTrue(CellTypeRecognizer.extractNumeric("1") == 1);
        assertTrue(CellTypeRecognizer.extractNumeric("100") == 100);
    }

}
