package home.stetsenko;

import home.stetsenko.exceptions.IllegalInputFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.util.Scanner;

public class Main {

    private static Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        try (Scanner stdin = new Scanner(new BufferedInputStream(System.in))) {

            SpreadsheetInputReader spreadsheetInputReader = new SpreadsheetInputReader();
            spreadsheetInputReader.readInput(stdin);

        } catch (IllegalInputFormatException e1) {
            LOGGER.error(e1.getMessage());
        }
    }



}
