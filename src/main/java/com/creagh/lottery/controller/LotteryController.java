package com.creagh.lottery.controller;

import com.creagh.lottery.dto.LineDto;
import com.creagh.lottery.dto.TicketResponseDto;
import com.creagh.lottery.dto.TicketResultResponseDto;
import com.creagh.lottery.service.TicketService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LotteryController {

    public static final String STANDARD_TICKET_TAG = "Standard Ticket";
    @Autowired
    TicketService standardTicketService;

    @PostMapping("/ticket")
    @ApiOperation(value = "Create a new standard ticket.", tags = STANDARD_TICKET_TAG)
    public ResponseEntity<TicketResponseDto> createNewTicket(@RequestBody List<LineDto> lines) {
        return standardTicketService.createTicket(lines);
    }

    @PostMapping("/ticket/random")
    @ApiOperation(value = "Create a new random standard ticket.", tags = STANDARD_TICKET_TAG)
    public ResponseEntity<TicketResponseDto> createNewRandomTicket(@RequestBody int numberOfLines) {
        return standardTicketService.createRandomTicket(numberOfLines);
    }

    @GetMapping("/ticket")
    @ApiOperation(value = "Return a list of all available tickets.", tags = STANDARD_TICKET_TAG)
    public ResponseEntity<List<TicketResponseDto>> getTickets() {
        return standardTicketService.getAllTickets();
    }

    @GetMapping("/ticket/{id}")
    @ApiOperation(value = "Return a specified ticket.", tags = STANDARD_TICKET_TAG)
    public ResponseEntity<TicketResponseDto> getTicket(@RequestParam int id) {
        return standardTicketService.getTicket(id);
    }

    @PutMapping("/ticket/{id}")
    @ApiOperation(value = "Amend a ticket with new lines", tags = STANDARD_TICKET_TAG)
    public ResponseEntity<TicketResponseDto> updateTicket(@RequestParam int id, @RequestBody List<LineDto> lines) {
        return standardTicketService.updateTicket(id, lines);
    }

    @PutMapping("/ticket/random/{id}")
    @ApiOperation(value = "Amend a ticket with new random lines", tags = STANDARD_TICKET_TAG)
    public ResponseEntity<TicketResponseDto> updateTicket(@RequestParam int id, @RequestBody int numberOflines) {
        return standardTicketService.updateTicketWithRandomLines(id, numberOflines);
    }

    @PutMapping("/ticket/status/{id}")
    @ApiOperation(value = "Check the result of ticket", tags = STANDARD_TICKET_TAG)
    public ResponseEntity<TicketResultResponseDto> checkTicketStatus(@RequestParam int id) {
        return standardTicketService.checkStatus(id);
    }
}
