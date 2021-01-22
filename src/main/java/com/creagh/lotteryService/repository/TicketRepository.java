package com.creagh.lotteryService.repository;

import com.creagh.lotteryService.entity.StandardTicket;

import java.util.List;

public interface TicketRepository {

    StandardTicket saveTicket(StandardTicket ticketRequestDto);

    StandardTicket findTicketById(int id);

    StandardTicket updateTicket(StandardTicket standardTicket);

    List<StandardTicket> findAllTickets();
}
