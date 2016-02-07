import home.stetsenko.SpreadsheetConstants;
import home.stetsenko.SpreadsheetInputReader;
import home.stetsenko.SpreadsheetUtils;
import home.stetsenko.exceptions.IllegalInputFormatException;
import home.stetsenko.exceptions.NonExistingReferenceException;
import home.stetsenko.model.cell.Cell;
import home.stetsenko.model.row.Row;
import home.stetsenko.model.sheet.Sheet;
import home.stetsenko.processing.SheetProcessor;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Scanner;

import static org.junit.Assert.fail;

public class TestSheetProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestSheetProcessor.class);

    @Test
         public void test_baseExample() {

        ClassLoader classLoader = (TestSpreadsheetOutput.class).getClassLoader();
        @SuppressWarnings("ConstantConditions") File file = new File(classLoader.getResource("example0.txt").getFile());

        String[][] expectedArray = new String[][] {
                {"12", "-4", "3", "Sample"},
                {"4", "-16", "-4", "Spread"},
                {"Test", "1", "5", "Sheet"}
        };

        compareWithExpected(file, expectedArray);

    }

    @Test
    public void test_stringRefs() {

        ClassLoader classLoader = (TestSpreadsheetOutput.class).getClassLoader();
        File file = new File(classLoader.getResource("example2.txt").getFile());

        String[][] expectedArray = new String[][] {
                {"Sample", "Sample", "Sample"}
        };

        compareWithExpected(file, expectedArray);

    }

    @Test
    public void test_blankValues() {

        ClassLoader classLoader = (TestSpreadsheetOutput.class).getClassLoader();
        File file = new File(classLoader.getResource("example3.txt").getFile());

        String[][] expectedArray = new String[][] {
                {"<blank>", "#REF!", "5"}
        };

        compareWithExpected(file, expectedArray);

    }

    @Test
         public void test_divZero() {

        ClassLoader classLoader = (TestSpreadsheetOutput.class).getClassLoader();
        File file = new File(classLoader.getResource("example4.txt").getFile());

        String[][] expectedArray = new String[][] {
                {"0", "#DIV/0!", "5"}
        };

        compareWithExpected(file, expectedArray);

    }

    @Test
    public void test_refToErrorRef() {

        ClassLoader classLoader = (TestSpreadsheetOutput.class).getClassLoader();
        File file = new File(classLoader.getResource("example5.txt").getFile());

        String[][] expectedArray = new String[][] {
                {"0", "#DIV/0!", "#REF!"},
                {"1", "#DIV/0!", "#REF!"},
                {"#REF!", "0", "7"}
        };

        compareWithExpected(file, expectedArray);

    }

    @Test
    public void test_simpleCalculation() {

        ClassLoader classLoader = (TestSpreadsheetOutput.class).getClassLoader();
        File file = new File(classLoader.getResource("example6.txt").getFile());

        String[][] expectedArray = new String[][] {
                {"1", "2", "5"}
        };

        compareWithExpected(file, expectedArray);

    }

    @Test
    public void test_nonExistingRef() {

        ClassLoader classLoader = (TestSpreadsheetOutput.class).getClassLoader();
        File file = new File(classLoader.getResource("example7.txt").getFile());

        String[][] expectedArray = new String[][] {
                {"1", "#NON_EXIST_REF!", "#REF!"}
        };

        compareWithExpected(file, expectedArray);

    }

    private void compareWithExpected(File file, String[][] expectedArray) {

        //finally is not needed
        try (Scanner stdin = new Scanner(file)) {

            //stdin = new Scanner(file);
            LOGGER.debug("Scanner is initialized");
            SpreadsheetInputReader spreadsheetInputReader = new SpreadsheetInputReader(stdin);
            spreadsheetInputReader.readInput();

            Sheet sheet = spreadsheetInputReader.getSheet();

            SpreadsheetUtils.printSheet(sheet);

            if (sheet.getNumberOfRows() == 0 || sheet.getRow(0).getLastCellNum() == 0) {
                fail("Empty sheet");
            }

            Sheet calculatedSheet = new SheetProcessor().process(sheet);

            SpreadsheetUtils.printSheet(calculatedSheet);

            //convert sheet to array
            String[][] actualArray = new String[calculatedSheet.getNumberOfRows()][calculatedSheet.getRow(0).getLastCellNum()];
            Iterator<Row> rowIterator = calculatedSheet.rowIterator();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    actualArray[cell.getCellReference().getRowIndex()][cell.getCellReference().getColIndex()] = cell.getCellRepresentation();
                }
            }

            Assert.assertArrayEquals(expectedArray, actualArray);

        } catch (FileNotFoundException e) {
            LOGGER.error(SpreadsheetConstants.MESSAGE_FILE_WAS_NOT_FOUND, e);
            fail(SpreadsheetConstants.MESSAGE_FILE_WAS_NOT_FOUND);
        } catch (IllegalInputFormatException | NonExistingReferenceException e) {
            LOGGER.error(e.getMessage(), e);
            fail(e.getMessage());
        }
    }

}
