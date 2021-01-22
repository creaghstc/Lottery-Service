package com.creagh.lotteryService.service;

import com.creagh.lotteryService.dto.LineDto;
import com.creagh.lotteryService.dto.TicketResponseDto;
import com.creagh.lotteryService.dto.TicketResultResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.List;

import static com.creagh.lotteryService.constants.StandardTicketConstants.RESULT_TEN;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations="classpath:test.properties")
class StandardTicketServiceImplTest {

    @Autowired
    TicketService standardTicketService;

    List<LineDto> lineDtoList = new ArrayList();

    private static final String INVALID_LINE_RESPONSE = "Invalid Ticket line in request";

    @BeforeEach
    void setUp() {

        LineDto lineDto = new LineDto(1, 0, 1);
        lineDtoList.add(lineDto);

    }

    @Test
    void getAllTickets() {

        //Given
        //When
        standardTicketService.createTicket(lineDtoList);
        standardTicketService.createTicket(lineDtoList);
        standardTicketService.createTicket(lineDtoList);
        List<TicketResponseDto> responseDtoList = (List<TicketResponseDto>) standardTicketService.getAllTickets().getBody();

        //Then
        assertTrue(responseDtoList.size() >= 3);
        assertFalse(responseDtoList.isEmpty());
    }

    @Test
    void createTicketNoLines() {

        //Given
        lineDtoList.clear();

        //When
        ResponseEntity responseEntity = standardTicketService.createTicket(lineDtoList);

        //Then
        assertNotNull(responseEntity);
        assertEquals(INVALID_LINE_RESPONSE, responseEntity.getBody());
    }

    @Test
    void createTicket() {

        //When
        ResponseEntity responseEntity = standardTicketService.createTicket(lineDtoList);

        //Then
        assertNotNull(responseEntity);
        assertEquals(TicketResponseDto.class, responseEntity.getBody().getClass());
    }

    @Test
    void updateTicket() {

        //Given
        TicketResponseDto createResponse = (TicketResponseDto) standardTicketService.createTicket(lineDtoList).getBody();

        int ticketId = createResponse.getId();
        lineDtoList.clear();

        LineDto newLine = new LineDto(1, 2, 1);
        lineDtoList.add(newLine);

        //When
        TicketResponseDto updatedResponse = (TicketResponseDto) standardTicketService.updateTicket(ticketId, lineDtoList).getBody();

        //Then
        assertEquals(2, updatedResponse.getLines().size());
        assertEquals(newLine.getNumber_one(), updatedResponse.getLines().get(1).getNumber_one());

    }

    @Test
    void checkStatus() {

        //Given
        //When
        TicketResponseDto createResponse = (TicketResponseDto) standardTicketService.createTicket(lineDtoList).getBody();
        TicketResultResponseDto responseDto = (TicketResultResponseDto) standardTicketService.checkStatus(createResponse.getId()).getBody();

        //Then
        assertFalse(responseDto.getResultTenGroup().isEmpty());
    }


    @Test
    void getTicket() {

        //Given
        //When
        TicketResponseDto createResponse = (TicketResponseDto) standardTicketService.createTicket(lineDtoList).getBody();
        TicketResponseDto getResponse = (TicketResponseDto) standardTicketService.getTicket(createResponse.getId()).getBody();

        //Then
        int expectedNumberOne = lineDtoList.get(0).getNumber_one();
        int actualNumberOne = getResponse.getLines().get(0).getNumber_one();

        assertNotNull(getResponse);
        assertEquals(expectedNumberOne, actualNumberOne);
    }
}