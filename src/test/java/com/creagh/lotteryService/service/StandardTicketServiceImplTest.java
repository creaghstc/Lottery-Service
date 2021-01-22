package com.creagh.lotteryService.service;

import com.creagh.lotteryService.dto.LineDto;
import com.creagh.lotteryService.dto.TicketRequestDto;
import com.creagh.lotteryService.dto.TicketResponseDto;
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

    TicketRequestDto ticketRequestDto;
    List<LineDto> lineDtoList = new ArrayList();

    private static final String INVALID_LINE_RESPONSE = "Invalid Ticket line in request";

    @BeforeEach
    void setUp() {

        ticketRequestDto = new TicketRequestDto();

        LineDto lineDto = new LineDto(1, 0, 1);
        lineDtoList.add(lineDto);

    }

    @Test
    void getAllTickets() {

        //Given
        ticketRequestDto.setLines(lineDtoList);

        //When
        standardTicketService.createTicket(ticketRequestDto);
        standardTicketService.createTicket(ticketRequestDto);
        standardTicketService.createTicket(ticketRequestDto);
        List<TicketResponseDto> responseDtoList = (List<TicketResponseDto>) standardTicketService.getAllTickets().getBody();

        //Then
        assertTrue(responseDtoList.size() >= 3);
        assertFalse(responseDtoList.isEmpty());
    }

    @Test
    void createTicketNoLines() {

        //When
        ResponseEntity responseEntity = standardTicketService.createTicket(ticketRequestDto);

        //Then
        assertNotNull(responseEntity);
        assertEquals(INVALID_LINE_RESPONSE, responseEntity.getBody());
    }

    @Test
    void createTicket() {

        //Given
        ticketRequestDto.setLines(lineDtoList);

        //When
        ResponseEntity responseEntity = standardTicketService.createTicket(ticketRequestDto);

        //Then
        assertNotNull(responseEntity);
        assertEquals(TicketResponseDto.class, responseEntity.getBody().getClass());
    }

    @Test
    void updateTicket() {

        //Given
        ticketRequestDto.setLines(lineDtoList);
        TicketResponseDto createResponse = (TicketResponseDto) standardTicketService.createTicket(ticketRequestDto).getBody();

        int ticketId = createResponse.getId();
        lineDtoList.clear();

        LineDto newLine = new LineDto(1, 2, 1);
        ticketRequestDto.setLines(lineDtoList);
        lineDtoList.add(newLine);

        //When
        TicketResponseDto updatedResponse = (TicketResponseDto) standardTicketService.updateTicket(ticketId, ticketRequestDto).getBody();

        //Then
        assertEquals(2, updatedResponse.getLines().size());
        assertEquals(newLine.getNumber_one(), updatedResponse.getLines().get(1).getNumber_one());

    }

    @Test
    void checkStatus() {

        //Given
        ticketRequestDto.setLines(lineDtoList);

        //When
        TicketResponseDto createResponse = (TicketResponseDto) standardTicketService.createTicket(ticketRequestDto).getBody();
        TicketResponseDto responseDto = (TicketResponseDto) standardTicketService.checkStatus(createResponse.getId()).getBody();

        //Then
        assertEquals(RESULT_TEN, responseDto.getLines().get(0).getResult());
    }


    @Test
    void getTicket() {

        //Given
        ticketRequestDto.setLines(lineDtoList);

        //When
        TicketResponseDto createResponse = (TicketResponseDto) standardTicketService.createTicket(ticketRequestDto).getBody();
        TicketResponseDto getResponse = (TicketResponseDto) standardTicketService.getTicket(createResponse.getId()).getBody();

        //Then
        int expectedNumberOne = ticketRequestDto.getLines().get(0).getNumber_one();
        int actualNumberOne = getResponse.getLines().get(0).getNumber_one();

        assertNotNull(getResponse);
        assertEquals(expectedNumberOne, actualNumberOne);
    }
}