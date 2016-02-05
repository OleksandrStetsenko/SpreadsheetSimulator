import home.stetsenko.model.cell.CellParser;
import home.stetsenko.model.cell.CellType;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertTrue;

public class TestCellParsing {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestCellParsing.class);

    @Test
    public void test_recognizeType() {
        assertTrue(CellType.CELL_TYPE_BLANK.equals(CellParser.recognizeType("")));

        assertTrue(CellType.CELL_TYPE_STRING.equals(CellParser.recognizeType("'Sample")));
        assertTrue(CellType.CELL_TYPE_STRING.equals(CellParser.recognizeType("'Sam'ple ")));

        assertTrue(CellType.CELL_TYPE_NUMERIC.equals(CellParser.recognizeType("4")));
        assertTrue(CellType.CELL_TYPE_NUMERIC.equals(CellParser.recognizeType("55")));

        assertTrue(CellType.CELL_TYPE_EXPRESSION.equals(CellParser.recognizeType("=5")));
        assertTrue(CellType.CELL_TYPE_EXPRESSION.equals(CellParser.recognizeType("=A1")));
        assertTrue(CellType.CELL_TYPE_EXPRESSION.equals(CellParser.recognizeType("=A1+A2")));
        assertTrue(CellType.CELL_TYPE_EXPRESSION.equals(CellParser.recognizeType("=5-3")));
        assertTrue(CellType.CELL_TYPE_EXPRESSION.equals(CellParser.recognizeType("=A1+5")));
        assertTrue(CellType.CELL_TYPE_EXPRESSION.equals(CellParser.recognizeType("=5*A1")));
        assertTrue(CellType.CELL_TYPE_EXPRESSION.equals(CellParser.recognizeType("=5/A1")));
        assertTrue(CellType.CELL_TYPE_EXPRESSION.equals(CellParser.recognizeType("=A1+B3*5/C4-R4")));

        assertTrue(CellType.CELL_TYPE_ERROR.equals(CellParser.recognizeType("#")));
        assertTrue(CellType.CELL_TYPE_ERROR.equals(CellParser.recognizeType("=A1++A2")));
    }

    @Test
    public void test_extractText() {
        assertTrue("Sample".equals(CellParser.extractText("'Sample")));
        assertTrue("Sam'ple".equals(CellParser.extractText("'Sam'ple")));
    }

    @Test
    public void test_extractNumeric() {
        assertTrue(CellParser.extractNumeric("55") == 55);
        assertTrue(CellParser.extractNumeric("1") == 1);
        assertTrue(CellParser.extractNumeric("100") == 100);
    }

}
