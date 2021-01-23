package com.creagh.lottery.util;

import com.creagh.lottery.dto.LineDto;
import com.creagh.lottery.dto.TicketResponseDto;
import com.creagh.lottery.dto.TicketResultResponseDto;
import com.creagh.lottery.entity.StandardLine;
import com.creagh.lottery.entity.StandardTicket;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.List;

import static com.creagh.lottery.constants.StandardTicketConstants.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
class TicketUtilTest {

    @Autowired
    TicketUtil ticketUtil;

    @Test
    void lineDtoToEntityTest() {

        //Given
        LineDto lineDto = new LineDto(1, 2, 2);
        StandardTicket standardTicket = new StandardTicket();
        standardTicket.setId(2);

        List<LineDto> lineDtos = new ArrayList();
        lineDtos.add(lineDto);

        //When
        List<StandardLine> standardLines = ticketUtil.lineDtoToEntity(lineDtos, standardTicket);

        //Then
        assertFalse(standardLines.isEmpty());
        assertEquals(standardLines.get(0).getClass(), StandardLine.class);
    }

    @Test
    void entityToResponseTest() {

        //Given
        List<StandardLine> standardLines = new ArrayList();
        StandardTicket standardTicket = new StandardTicket();
        standardTicket.setId(2);
        StandardLine line = new StandardLine(1, 2, 2);
        line.setTicket(standardTicket);
        standardLines.add(line);
        standardTicket.setLines(standardLines);

        //When
        TicketResponseDto ticketResponseDto = ticketUtil.entityToResponse(standardTicket);

        //Then
        assertFalse(ticketResponseDto.getLines().isEmpty());
    }

    @Test
    void calculateTicketResultsTest() {

        //Given
        List<StandardLine> standardLines = new ArrayList();
        StandardLine resultTen = new StandardLine(1, 1, 0);
        StandardLine resultFive = new StandardLine(1, 1, 1);
        StandardLine resultOne = new StandardLine(0, 1, 2);
        StandardLine resultZero = new StandardLine(1, 1, 2);


        standardLines.add(resultOne);
        standardLines.add(resultFive);
        standardLines.add(resultTen);
        standardLines.add(resultZero);

        //When
        ticketUtil.calculateTicketResults(standardLines);

        //Then
        assertEquals(RESULT_ONE, standardLines.get(0).getResult());
        assertEquals(RESULT_FIVE, standardLines.get(1).getResult());
        assertEquals(RESULT_TEN, standardLines.get(2).getResult());
        assertEquals(RESULT_ZERO, standardLines.get(3).getResult());
    }

    @Test
    void entityToResultResponseDtoTest() {

        //Given
        List<StandardLine> standardLines = new ArrayList();
        StandardTicket standardTicket = new StandardTicket();
        standardTicket.setId(2);
        StandardLine line = new StandardLine(1, 2, 2);
        line.setTicket(standardTicket);
        standardLines.add(line);
        standardTicket.setLines(ticketUtil.calculateTicketResults(standardLines));

        //When
        TicketResultResponseDto ticketResultResponseDto = ticketUtil.entityToResultResponseDto(standardTicket);

        //Then
        assertFalse(ticketResultResponseDto.getResultGroup1().isEmpty());
        assertTrue(ticketResultResponseDto.getResultGroup10().isEmpty());
    }
}