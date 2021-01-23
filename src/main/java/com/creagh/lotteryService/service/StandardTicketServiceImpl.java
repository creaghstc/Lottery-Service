package com.creagh.lotteryService.service;

import com.creagh.lotteryService.dto.LineDto;
import com.creagh.lotteryService.dto.TicketResponseDto;
import com.creagh.lotteryService.dto.TicketResultResponseDto;
import com.creagh.lotteryService.entity.StandardLine;
import com.creagh.lotteryService.entity.StandardTicket;
import com.creagh.lotteryService.repository.TicketRepository;
import com.creagh.lotteryService.util.TicketUtil;
import com.creagh.lotteryService.util.ValidationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.creagh.lotteryService.constants.StandardTicketConstants.TICKET_CHECKED;
import static com.creagh.lotteryService.constants.StandardTicketConstants.TICKET_NOT_CHECKED;

@Service
public class StandardTicketServiceImpl implements TicketService {

    public static final String INVALID_TICKET_LINE_IN_REQUEST = "Invalid Ticket line in request";
    public static final String UNABLE_TO_FIND_TICKET_WITH_ID = "Unable to find ticket with ID: ";
    public static final String COULD_NOT_FIND_TICKET_WITH_ID_LOG = "Could not find ticket with ID: {}";
    private static Logger logger = LoggerFactory.getLogger(StandardTicketServiceImpl.class);

    @Autowired
    TicketRepository standardTicketRepository;
    @Autowired
    TicketUtil ticketUtil;
    @Autowired
    ValidationUtil validationUtil;

    /**
     * Method to create a new ticket with n rows.
     *
     * @param lines
     * @return
     */
    @Override
    public ResponseEntity createTicket(List<LineDto> lines) {

        if (validationUtil.validateLines(lines)) {
            StandardTicket standardTicket = new StandardTicket();
            standardTicket.setStatusChecked(TICKET_NOT_CHECKED);

            standardTicket = standardTicketRepository.saveTicket(standardTicket);
            standardTicket.setLines(ticketUtil.lineDtoToEntity(lines, standardTicket));
            standardTicketRepository.updateTicket(standardTicket);

            logger.info("Created new Standard ticket with ID: {}", standardTicket.getId());
            return ResponseEntity.ok(ticketUtil.entityToResponse(standardTicket));
        } else {
            logger.info("Invalid line found for create ticket request.");
            return ResponseEntity.badRequest().body(INVALID_TICKET_LINE_IN_REQUEST);
        }
    }

    /**
     * Method to create a new ticket of {@code numberOfLines} lenght
     * @param numberOfLines
     * @return
     */
    @Override
    public ResponseEntity<TicketResponseDto> createRandomTicket(int numberOfLines) {

        List<LineDto> generatedLines;

        generatedLines = ticketUtil.generateRandomTicket(numberOfLines);

        return createTicket(generatedLines);
    }

    /**
     * Method to amend n rows to a given ticket.
     *
     * @param id
     * @param lines
     * @return
     */
    @Override
    public ResponseEntity updateTicket(int id, List<LineDto> lines) {

        StandardTicket standardTicket = standardTicketRepository.findTicketById(id);

        if (null != standardTicket) {

            if (standardTicket.getStatusChecked().equals(TICKET_NOT_CHECKED)) {
                ArrayList<StandardLine> totalLines = new ArrayList<>();
                totalLines.addAll((standardTicket.getLines()));

                if (validationUtil.validateLines(lines)) {

                    totalLines.addAll(ticketUtil.lineDtoToEntity(lines, standardTicket));
                    standardTicket.setLines(totalLines);
                    standardTicketRepository.updateTicket(standardTicket);
                    logger.info("Successfully updated ticket {}", id);
                    return ResponseEntity.ok(ticketUtil.entityToResponse(standardTicket));

                } else {
                    logger.info("Tried to update ticket {}, with invalid line.", id);
                    return ResponseEntity.badRequest().body(INVALID_TICKET_LINE_IN_REQUEST);
                }
            } else {
                logger.info("Tried to amend ticket, {}, which has alread been checked", id);
                return ResponseEntity.badRequest().body("Unable to update a ticket which has been checked");

            }
        } else {
            logger.info(COULD_NOT_FIND_TICKET_WITH_ID_LOG, id);
            return ResponseEntity.badRequest().body(UNABLE_TO_FIND_TICKET_WITH_ID + id);
        }


    }

    /**
     * Method to update a given ticket with {@code numberOfLines} new random lines.
     * @param id
     * @param numberOfLines
     * @return
     */
    @Override
    public ResponseEntity<TicketResponseDto> updateTicketWithRandomLines(int id, int numberOfLines) {
        List<LineDto> generatedLines;

        generatedLines = ticketUtil.generateRandomTicket(numberOfLines);

        return updateTicket(id, generatedLines);    }

    /**
     * Method to check the results of a given ticket id.
     *
     * @param id
     * @return
     */
    @Override
    public ResponseEntity checkStatus(int id) {

        StandardTicket standardTicket = standardTicketRepository.findTicketById(id);

        if (null != standardTicket) {

            standardTicket.setLines(ticketUtil.calculateTicketResults(standardTicket.getLines()));
            standardTicket.setStatusChecked(TICKET_CHECKED);
            standardTicketRepository.updateTicket(standardTicket);

            TicketResultResponseDto response = ticketUtil.entityToResultResponseDto(standardTicket);

            logger.info("Returning checked ticket results {}", id);
            return ResponseEntity.ok(response);

        } else {
            logger.info(COULD_NOT_FIND_TICKET_WITH_ID_LOG, id);
            return ResponseEntity.badRequest().body(UNABLE_TO_FIND_TICKET_WITH_ID + id);
        }
    }

    /**
     * Method to return all available tickets.
     *
     * @return
     */
    @Override
    public ResponseEntity getAllTickets() {
        List<StandardTicket> tickets = standardTicketRepository.findAllTickets();

        List<TicketResponseDto> responseDtoList = new ArrayList<>();

        for (StandardTicket ticket : tickets) {
            responseDtoList.add(ticketUtil.entityToResponse(ticket));
        }

        if (responseDtoList.isEmpty()) {
            logger.info("Get all tickets request returned empty list");
            return ResponseEntity.ok().body("No available tickets");
        }
        return ResponseEntity.ok(responseDtoList);
    }

    /**
     * Method to return a ticket with given id.
     *
     * @param id
     * @return
     */
    @Override
    public ResponseEntity getTicket(int id) {

        StandardTicket standardTicket = standardTicketRepository.findTicketById(id);

        if (null != standardTicket) {
            if (standardTicket.getStatusChecked().equals(TICKET_CHECKED)){
                logger.info("Returning final ticket {}", id);
                return ResponseEntity.ok(ticketUtil.entityToResultResponseDto(standardTicket));
            } else {
                logger.info("Returning ticket {}", id);
                return ResponseEntity.ok(ticketUtil.entityToResponse(standardTicket));
            }
        } else {
            logger.info(COULD_NOT_FIND_TICKET_WITH_ID_LOG, id);
            return ResponseEntity.badRequest().body(UNABLE_TO_FIND_TICKET_WITH_ID + id);
        }
    }
}
