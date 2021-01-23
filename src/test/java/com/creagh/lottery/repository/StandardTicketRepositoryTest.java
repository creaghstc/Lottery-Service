package com.creagh.lottery.repository;

import com.creagh.lottery.entity.StandardLine;
import com.creagh.lottery.entity.StandardTicket;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.List;

import static com.creagh.lottery.constants.StandardTicketConstants.TICKET_CHECKED;
import static com.creagh.lottery.constants.StandardTicketConstants.TICKET_NOT_CHECKED;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
class StandardTicketRepositoryTest {

    @Autowired
    TicketRepository standardTicketRepository;

    StandardTicket ticket;
    ArrayList<StandardLine> lines;

    @BeforeEach
    void setUp() {

        StandardLine line = new StandardLine(1, 2, 2);
        lines = new ArrayList();
        lines.add(line);

        ticket = new StandardTicket();
    }

    @Test
    void saveTicketTest() {
        //Given
        StandardTicket newTicket = new StandardTicket();
        newTicket.setStatusChecked(TICKET_NOT_CHECKED);

        //When
        standardTicketRepository.saveTicket(newTicket);

        //Then
        assertNotEquals(0, newTicket.getId());
    }

    @Test
    void updateTicketTest() {

        //Given
        StandardTicket resultTicket;
        standardTicketRepository.saveTicket(ticket);
        ticket = standardTicketRepository.findTicketById(ticket.getId());

        ticket.setStatusChecked(TICKET_CHECKED);
        lines.get(0).setTicket(ticket);
        ticket.setLines(lines);

        //When
        standardTicketRepository.updateTicket(ticket);
        resultTicket = standardTicketRepository.findTicketById(ticket.getId());

        //Then
        assertEquals(TICKET_CHECKED, resultTicket.getStatusChecked());
        assertFalse(resultTicket.getLines().isEmpty());
    }

    @Test
    void findAllTicketsTest() {

        //Given
        standardTicketRepository.saveTicket(ticket);
        standardTicketRepository.saveTicket(new StandardTicket());
        standardTicketRepository.saveTicket(new StandardTicket());

        //When
        List<StandardTicket> resultList = standardTicketRepository.findAllTickets();

        //Then
        assertFalse(resultList.isEmpty());
    }

    @Test
    void findTicketByIdTest() {

        //GIven
        standardTicketRepository.saveTicket(ticket);

        //When
        StandardTicket standardTicket = standardTicketRepository.findTicketById(ticket.getId());

        //Then
        assertNotNull(standardTicket);
    }
}