package com.creagh.lotteryService.util;

import com.creagh.lotteryService.dto.LineDto;
import com.creagh.lotteryService.dto.LineResultDto;
import com.creagh.lotteryService.dto.TicketResponseDto;
import com.creagh.lotteryService.entity.StandardLine;
import com.creagh.lotteryService.entity.StandardTicket;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.creagh.lotteryService.constants.StandardTicketConstants.*;

@Component
public class TicketUtil {

    /**
     * Convert a list of lineDtos to entities ready to be saved to db.
     *
     * @param lines
     * @param standardTicket
     * @return
     */
    public List<StandardLine> lineDtoToEntity(List<LineDto> lines, StandardTicket standardTicket) {

        ArrayList<StandardLine> standardLines = new ArrayList<>();

        StandardLine standardLine;
        for (LineDto line : lines) {

            standardLine = new StandardLine();

            standardLine.setTicket(standardTicket);
            standardLine.setNumber_one(line.getNumber_one());
            standardLine.setNumber_two(line.getNumber_two());
            standardLine.setNumber_three(line.getNumber_three());

            standardLines.add(standardLine);
        }

        return standardLines;
    }

    /**
     * Convert a Standard ticket entity to WS response object.
     *
     * @param ticket
     * @return
     */
    public TicketResponseDto entityToResponse(StandardTicket ticket) {

        TicketResponseDto ticketResponseDto = new TicketResponseDto();
        ArrayList<LineResultDto> lineDtos = new ArrayList<>();

        ticketResponseDto.setId(ticket.getId());
        ticketResponseDto.setStatus(ticket.getStatusChecked());
        LineResultDto lineDto;

        for (StandardLine line : ticket.getLines()) {

            lineDto = new LineResultDto(
                    line.getNumber_one(),
                    line.getNumber_two(),
                    line.getNumber_three(),
                    line.getResult()
            );

            lineDtos.add(lineDto);
        }

        ticketResponseDto.setLines(lineDtos);
        return ticketResponseDto;
    }

    /**
     * Calculates the results of a list of lines.
     *
     * @param lines
     * @return
     */
    public List<StandardLine> calculateTicketResults(List<StandardLine> lines) {

        for (StandardLine standardLine : lines) {

            if (standardLine.getNumber_one() + standardLine.getNumber_two() + standardLine.getNumber_three() == TICKET_RESULT_CONDITION_TWO) {
                standardLine.setResult(RESULT_TEN);
            } else if (areEqual(standardLine.getNumber_one(), standardLine.getNumber_two(), standardLine.getNumber_three())) {
                standardLine.setResult(RESULT_FIVE);
            } else if (standardLine.getNumber_one() != standardLine.getNumber_two() && standardLine.getNumber_one() != standardLine.getNumber_three()) {
                standardLine.setResult(RESULT_ONE);
            } else {
                standardLine.setResult(RESULT_ZERO);
            }
        }

        return lines;

    }

    private boolean areEqual(int numberOne, int numberTwo, int numberThree) {
        return numberOne == numberTwo && numberTwo == numberThree;
    }

}
