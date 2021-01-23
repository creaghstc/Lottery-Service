package com.creagh.lottery.service;

import com.creagh.lottery.dto.LineDto;
import com.creagh.lottery.dto.TicketResponseDto;
import com.creagh.lottery.dto.TicketResultResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TicketService {

    ResponseEntity<TicketResponseDto> createTicket(List<LineDto> lines);

    ResponseEntity<TicketResponseDto> createRandomTicket(int numberOfLines);

    ResponseEntity<TicketResponseDto> getTicket(int id);

    ResponseEntity<TicketResponseDto> updateTicket(int id, List<LineDto> lines);

    ResponseEntity<TicketResponseDto> updateTicketWithRandomLines(int id, int numberOfLines);

    ResponseEntity<TicketResultResponseDto> checkStatus(int id);

    ResponseEntity<List<TicketResponseDto>>getAllTickets();
}
