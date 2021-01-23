package com.creagh.lottery.constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class StandardTicketConstants {

    private StandardTicketConstants() {
    }

    public static final List<Integer> VALID_NUMBERS = new ArrayList<>(Arrays.asList(0, 1, 2));
    public static final String TICKET_CHECKED = "CHECKED";
    public static final String TICKET_NOT_CHECKED = "NOT_CHECKED";

    public static final int TICKET_RESULT_CONDITION_TWO = 2;
    public static final String RESULT_TEN = "10";
    public static final String RESULT_FIVE = "5";
    public static final String RESULT_ONE = "1";
    public static final String RESULT_ZERO = "0";

    public static final Random numberGenerator = new Random();
}
