package com.creagh.lottery.util;

import com.creagh.lottery.dto.LineDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations="classpath:test.properties")
class ValidationUtilTest {

    @Autowired
    ValidationUtil validationUtil;

    @Test
    void validateLinesTest() {

        //Given
        LineDto validLine = new LineDto(1,1,2);
        LineDto invalidLine = new LineDto(3,4,5);

        List<LineDto> validList = new ArrayList();
        List<LineDto> invalidList = new ArrayList();

        //When
        validList.add(validLine);
        invalidList.add(invalidLine);

        //Then
        assertTrue(validationUtil.validateLines(validList));
        assertFalse(validationUtil.validateLines(invalidList));
    }
}