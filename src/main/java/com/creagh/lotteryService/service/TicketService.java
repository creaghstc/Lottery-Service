package com.creagh.lotteryService.service;

import com.creagh.lotteryService.dto.TicketRequestDto;
import com.creagh.lotteryService.dto.TicketResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TicketService {

    ResponseEntity<TicketResponseDto> createTicket(TicketRequestDto ticketRequestDto);

    ResponseEntity<TicketResponseDto> getTicket(int id);

    ResponseEntity<TicketResponseDto> updateTicket(int id, TicketRequestDto ticketRequestDto);

    ResponseEntity<TicketResponseDto> checkStatus(int id);

    ResponseEntity<List<TicketResponseDto>>getAllTickets();
}
