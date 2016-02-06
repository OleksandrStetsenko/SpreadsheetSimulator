import home.stetsenko.exceptions.IllegalInputFormatException;
import home.stetsenko.SpreadsheetConstants;
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

    @Test
    public void testSheetOutput() {

        Scanner stdin = null;
        try {
            ClassLoader classLoader = (TestSpreadsheetOutput.class).getClassLoader();
            File file = new File(classLoader.getResource("example0.txt").getFile());
            stdin = new Scanner(file);
            LOGGER.debug("Scanner is initialized");

            SpreadsheetInputReader spreadsheetInputReader = new SpreadsheetInputReader(stdin);

            spreadsheetInputReader.readInput();
            Sheet sheet = spreadsheetInputReader.getSheet();
            assertNotNull("Array of cells is null", sheet);
            SpreadsheetUtils.printSheet(sheet);

        } catch (FileNotFoundException e) {
            LOGGER.error(SpreadsheetConstants.MESSAGE_FILE_WAS_NOT_FOUND, e);
            fail(SpreadsheetConstants.MESSAGE_FILE_WAS_NOT_FOUND);
        } catch (IllegalInputFormatException e) {
            fail(e.getMessage());
        }

    }

}
