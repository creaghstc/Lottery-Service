package com.creagh.lottery.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
class LineResultDtoTest {

    public static final String RESULT_TEN = "10";
    public static final String RESULT_FIVE = "5";
    public static final String RESULT_ONE = "1";
    ArrayList<LineResultDto> lineResultDtos = new ArrayList();

    @BeforeEach
    void setUp() {
        LineResultDto resultOne = new LineResultDto(1, 1, 0, RESULT_TEN);
        LineResultDto resultTwo = new LineResultDto(1, 1, 1, RESULT_FIVE);
        LineResultDto resultFive = new LineResultDto(1, 1, 1, RESULT_TEN);
        LineResultDto resultFour = new LineResultDto(1, 1, 1, RESULT_ONE);
        LineResultDto resultSix = new LineResultDto(1, 1, 1, RESULT_FIVE);
        LineResultDto resultThree = new LineResultDto(0, 1, 1, RESULT_ONE);

        lineResultDtos.add(resultOne);

        lineResultDtos.add(resultThree);
        lineResultDtos.add(resultTwo);
        lineResultDtos.add(resultFive);
        lineResultDtos.add(resultFour);
        lineResultDtos.add(resultSix);
    }

    @Test
    void orderTest() {
        //When
        Collections.sort(lineResultDtos);

        //Then
        assertEquals(RESULT_ONE, lineResultDtos.get(0).getResult());
        assertEquals(RESULT_FIVE, lineResultDtos.get(2).getResult());
        assertEquals(RESULT_TEN, lineResultDtos.get(5).getResult());
    }
}