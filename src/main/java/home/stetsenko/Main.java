package home.stetsenko;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    private static Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        Scanner stdin = null;
        try {

            ClassLoader classLoader = (Main.class).getClassLoader();
            File file = new File(classLoader.getResource("example0.txt").getFile());
            stdin = new Scanner(file);
            //stdin = new Scanner(new BufferedInputStream(System.in));
            SpreadsheetInputReader spreadsheetInputReader = new SpreadsheetInputReader(stdin);
            spreadsheetInputReader.readInput();
            String[][] cells = spreadsheetInputReader.getCells();

            SpreadsheetUtils.printArray(cells);

        } catch (FileNotFoundException e) {
            LOGGER.error("File was not found", e);
        }
    }



}
