package home.stetsenko.model;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Pattern;

public class CellReference {

    private static Logger LOGGER = LoggerFactory.getLogger(CellReference.class);

    /**
     * Matches a run of one letter followed by a run of one digit.
     * The run of letters is group 1 and the run of digits is group 2.
     */
    private static final Pattern CELL_REF_PATTERN = Pattern.compile("([A-Za-z]{1})([0-9]{1})");

    private final int rowIndex = 0;
    private final int colIndex = 0;

}
