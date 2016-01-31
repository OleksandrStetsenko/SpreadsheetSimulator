import home.stetsenko.SpreadsheetInputReader;
import home.stetsenko.SpreadsheetUtils;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SpreadsheetOutputTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpreadsheetOutputTest.class);
    private Scanner stdin = null;

    @Before
    public void init() {
        try {
            ClassLoader classLoader = (SpreadsheetOutputTest.class).getClassLoader();
            File file = new File(classLoader.getResource("example0.txt").getFile());
            stdin = new Scanner(file);
        } catch (FileNotFoundException e) {
            LOGGER.error("File was not found", e);
        }
    }

    @Test
    public void testOutput() {
        SpreadsheetInputReader spreadsheetInputReader = new SpreadsheetInputReader(stdin);
        spreadsheetInputReader.readInput();
        String[][] cells = spreadsheetInputReader.getCells();
        SpreadsheetUtils.printArray(cells);
    }

}
