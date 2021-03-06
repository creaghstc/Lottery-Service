package com.creagh.lottery.service;

import com.creagh.lottery.dto.LineDto;
import com.creagh.lottery.dto.TicketResponseDto;
import com.creagh.lottery.dto.TicketResultResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
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
    void getAllTicketsTest() {

        //Given
        standardTicketService.createTicket(lineDtoList);
        standardTicketService.createTicket(lineDtoList);
        standardTicketService.createTicket(lineDtoList);

        //When
        List<TicketResponseDto> responseDtoList = standardTicketService.getAllTickets().getBody();

        //Then
        assertTrue(responseDtoList.size() >= 3);
        assertFalse(responseDtoList.isEmpty());
    }

    @Test
    void createTicketNoLinesTest() {

        //Given
        lineDtoList.clear();

        //When
        ResponseEntity responseEntity = standardTicketService.createTicket(lineDtoList);

        //Then
        assertNotNull(responseEntity);
        assertEquals(INVALID_LINE_RESPONSE, responseEntity.getBody());
    }

    @Test
    void createTicketTest() {

        //When
        ResponseEntity responseEntity = standardTicketService.createTicket(lineDtoList);

        //Then
        assertNotNull(responseEntity);
        assertEquals(TicketResponseDto.class, responseEntity.getBody().getClass());
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @Test
    void updateTicketTest() {

        //Given
        TicketResponseDto createResponse = standardTicketService.createTicket(lineDtoList).getBody();

        int ticketId = createResponse.getId();
        lineDtoList.clear();

        LineDto newLine = new LineDto(1, 2, 1);
        lineDtoList.add(newLine);

        //When
        TicketResponseDto updatedResponse = standardTicketService.updateTicket(ticketId, lineDtoList).getBody();

        //Then
        assertEquals(2, updatedResponse.getLines().size());
        assertEquals(newLine.getNumberOne(), updatedResponse.getLines().get(1).getNumberOne());

    }

    @Test
    void checkStatusTest() {

        //Given
        TicketResponseDto createResponse = standardTicketService.createTicket(lineDtoList).getBody();

        //When
        TicketResultResponseDto responseDto = standardTicketService.checkStatus(createResponse.getId()).getBody();

        //Then
        assertFalse(responseDto.getResultGroup10().isEmpty());
    }


    @Test
    void getTicketTest() {

        //Given
        //When
        TicketResponseDto createResponse = standardTicketService.createTicket(lineDtoList).getBody();
        TicketResponseDto getResponse = standardTicketService.getTicket(createResponse.getId()).getBody();
        ResponseEntity getResponseNotFound = standardTicketService.getTicket(0);


        //Then
        int expectedNumberOne = lineDtoList.get(0).getNumberOne();
        int actualNumberOne = getResponse.getLines().get(0).getNumberOne();

        assertNotNull(getResponse);
        assertEquals(expectedNumberOne, actualNumberOne);
        assertEquals(HttpStatus.NOT_FOUND, getResponseNotFound.getStatusCode());
    }

    @Test
    void createRandomTicketTest() {
        //Given
        int numberOfLines = 5;

        //When
        TicketResponseDto ticketResponseDto = standardTicketService.createRandomTicket(numberOfLines).getBody();

        //Then
        assertEquals(numberOfLines, ticketResponseDto.getLines().size());
    }

    @Test
    void updateTicketWithRandomLinesTest() {

        //Given
        TicketResponseDto createResponse = standardTicketService.createRandomTicket(3).getBody();
        int ticketId = createResponse.getId();

        //When
        TicketResponseDto updatedResponse = standardTicketService.updateTicketWithRandomLines(ticketId, 2).getBody();

        //Then
        assertEquals(5, updatedResponse.getLines().size());
    }
}