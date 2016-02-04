import home.stetsenko.SpreadsheetInputReader;
import home.stetsenko.SpreadsheetUtils;
import home.stetsenko.model.sheet.Sheet;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

public class TestSpreadsheetOutput {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestSpreadsheetOutput.class);
    private Scanner stdin = null;

    @Before
    public void init() {
        try {
            ClassLoader classLoader = (TestSpreadsheetOutput.class).getClassLoader();
            File file = new File(classLoader.getResource("example0.txt").getFile());
            stdin = new Scanner(file);
            LOGGER.debug("Scanner is initialized");
        } catch (FileNotFoundException e) {
            LOGGER.error("File was not found", e);
            fail("File was not found");
        }
    }

    @Test
    public void testSheetOutput() {
        SpreadsheetInputReader spreadsheetInputReader = new SpreadsheetInputReader(stdin);
        spreadsheetInputReader.readInput();
        Sheet sheet = spreadsheetInputReader.getSheet();
        assertNotNull("Array of cells is null", sheet);
        SpreadsheetUtils.printSheet(sheet);
    }

}
