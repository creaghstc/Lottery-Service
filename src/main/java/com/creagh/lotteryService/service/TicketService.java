package com.creagh.lotteryService.service;

import com.creagh.lotteryService.dto.LineDto;
import com.creagh.lotteryService.dto.TicketResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TicketService {

    ResponseEntity<TicketResponseDto> createTicket(List<LineDto> lines);

    ResponseEntity<TicketResponseDto> getTicket(int id);

    ResponseEntity<TicketResponseDto> updateTicket(int id, List<LineDto> lines);

    ResponseEntity checkStatus(int id);

    ResponseEntity<List<TicketResponseDto>>getAllTickets();
}
