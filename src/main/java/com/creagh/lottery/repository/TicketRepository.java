package com.creagh.lottery.repository;

import com.creagh.lottery.entity.StandardTicket;

import java.util.List;

public interface TicketRepository {

    StandardTicket saveTicket(StandardTicket ticketRequestDto);

    StandardTicket findTicketById(int id);

    StandardTicket updateTicket(StandardTicket standardTicket);

    List<StandardTicket> findAllTickets();
}
