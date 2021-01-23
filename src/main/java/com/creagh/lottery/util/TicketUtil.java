package com.creagh.lottery.util;

import com.creagh.lottery.dto.LineDto;
import com.creagh.lottery.dto.LineResultDto;
import com.creagh.lottery.dto.TicketResponseDto;
import com.creagh.lottery.dto.TicketResultResponseDto;
import com.creagh.lottery.entity.StandardLine;
import com.creagh.lottery.entity.StandardTicket;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.creagh.lottery.constants.StandardTicketConstants.*;

@Component
public class TicketUtil {

    public static final int UPPER_BOUND = 3;

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
            standardLine.setNumberOne(line.getNumberOne());
            standardLine.setNumberTwo(line.getNumberTwo());
            standardLine.setNumberThree(line.getNumberThree());

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
        ArrayList<LineDto> lineDtos = new ArrayList<>();

        ticketResponseDto.setId(ticket.getId());
        ticketResponseDto.setStatus(ticket.getStatusChecked());
        LineDto lineDto;

        for (StandardLine line : ticket.getLines()) {

            lineDto = new LineDto(
                    line.getNumberOne(),
                    line.getNumberTwo(),
                    line.getNumberThree()
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

            if (standardLine.getNumberOne() + standardLine.getNumberTwo() + standardLine.getNumberThree() == TICKET_RESULT_CONDITION_TWO) {
                standardLine.setResult(RESULT_TEN);
            } else if (areEqual(standardLine.getNumberOne(), standardLine.getNumberTwo(), standardLine.getNumberThree())) {
                standardLine.setResult(RESULT_FIVE);
            } else if (standardLine.getNumberOne() != standardLine.getNumberTwo() && standardLine.getNumberOne() != standardLine.getNumberThree()) {
                standardLine.setResult(RESULT_ONE);
            } else {
                standardLine.setResult(RESULT_ZERO);
            }
        }

        return lines;
    }

    /**
     * Converts entity to a final response object which has the lines grouped by outcome.
     *
     * @param ticket
     * @return
     */
    public TicketResultResponseDto entityToResultResponseDto(StandardTicket ticket) {
        TicketResultResponseDto ticketResultResponseDto = new TicketResultResponseDto();
        ArrayList<LineResultDto> lineDtos = new ArrayList<>();

        ticketResultResponseDto.setId(ticket.getId());
        ticketResultResponseDto.setStatus(ticket.getStatusChecked());
        LineResultDto lineDto;

        for (StandardLine line : ticket.getLines()) {

            lineDto = new LineResultDto(
                    line.getNumberOne(),
                    line.getNumberTwo(),
                    line.getNumberThree(),
                    line.getResult()
            );

            lineDtos.add(lineDto);
        }

        addLinesToGroups(lineDtos, ticketResultResponseDto);
        return ticketResultResponseDto;
    }

    private boolean areEqual(int numberOne, int numberTwo, int numberThree) {
        return numberOne == numberTwo && numberTwo == numberThree;
    }

    /**
     * Adds lines to the outcome groups
     *
     * @param lineResultDtos
     * @param ticketResultResponseDto
     */
    private void addLinesToGroups(List<LineResultDto> lineResultDtos, TicketResultResponseDto ticketResultResponseDto) {

        for (LineResultDto line : lineResultDtos) {
            if (line.getResult().equals(RESULT_TEN)) {
                ticketResultResponseDto.getResultGroup10().add(line);
            } else if (line.getResult().equals(RESULT_FIVE)) {
                ticketResultResponseDto.getResultGroup5().add(line);
            } else if (line.getResult().equals(RESULT_ONE)) {
                ticketResultResponseDto.getResultGroup1().add(line);
            } else if (line.getResult().equals(RESULT_ZERO)) {
                ticketResultResponseDto.getResultGroup0().add(line);
            }
        }
    }

    public List<LineDto> generateRandomTicket(int numberOfLines) {

        LineDto newLine;
        List<LineDto> generatedLines = new ArrayList<>();


        while (generatedLines.size() < numberOfLines) {
            newLine = new LineDto();
            newLine.setNumberOne(numberGenerator.nextInt(UPPER_BOUND));
            newLine.setNumberTwo(numberGenerator.nextInt(UPPER_BOUND));
            newLine.setNumberThree(numberGenerator.nextInt(UPPER_BOUND));

            generatedLines.add(newLine);
        }

        return generatedLines;
    }

}
