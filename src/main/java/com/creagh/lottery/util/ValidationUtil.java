package com.creagh.lottery.util;

import com.creagh.lottery.dto.LineDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.creagh.lottery.constants.StandardTicketConstants.VALID_NUMBERS;

@Component
public class ValidationUtil {

    public static final String INVALID_NUMBER_IN_LINE_LOG = "Invalid number ({}) in line.";
    private static Logger logger = LoggerFactory.getLogger(ValidationUtil.class);

    /**
     * Method to check that a line has only valid number (0, 1, 2)
     *
     * @param lines
     * @return
     */
    public boolean validateLines(List<LineDto> lines) {

        if (lines != null && !lines.isEmpty()) {
            for (LineDto line : lines) {
                if (!VALID_NUMBERS.contains(line.getNumberOne())) {
                    logger.info(INVALID_NUMBER_IN_LINE_LOG, line.getNumberOne());
                    return false;
                } else if (!VALID_NUMBERS.contains(line.getNumberTwo())) {
                    logger.info(INVALID_NUMBER_IN_LINE_LOG, line.getNumberTwo());
                    return false;
                } else if (!VALID_NUMBERS.contains(line.getNumberThree())) {
                    logger.info(INVALID_NUMBER_IN_LINE_LOG, line.getNumberThree());
                    return false;
                }
            }
        } else {
            return false;
        }

        return true;
    }
}
