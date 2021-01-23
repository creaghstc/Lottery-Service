package com.creagh.lotteryService.util;

import com.creagh.lotteryService.dto.LineDto;
import com.creagh.lotteryService.dto.LineResultDto;
import com.creagh.lotteryService.dto.TicketResponseDto;
import com.creagh.lotteryService.dto.TicketResultResponseDto;
import com.creagh.lotteryService.entity.StandardLine;
import com.creagh.lotteryService.entity.StandardTicket;
import org.springframework.stereotype.Component;

import javax.sound.sampled.Line;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static com.creagh.lotteryService.constants.StandardTicketConstants.*;

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
        ArrayList<LineDto> lineDtos = new ArrayList<>();

        ticketResponseDto.setId(ticket.getId());
        ticketResponseDto.setStatus(ticket.getStatusChecked());
        LineDto lineDto;

        for (StandardLine line : ticket.getLines()) {

            lineDto = new LineDto(
                    line.getNumber_one(),
                    line.getNumber_two(),
                    line.getNumber_three()
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

    /**
     * Converts entity to a final response object which has the lines grouped by outcome.
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
                    line.getNumber_one(),
                    line.getNumber_two(),
                    line.getNumber_three(),
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
     * @param lineResultDtos
     * @param ticketResultResponseDto
     */
    private void addLinesToGroups(List<LineResultDto> lineResultDtos, TicketResultResponseDto ticketResultResponseDto) {

        for (LineResultDto line : lineResultDtos) {
            if (line.getResult().equals(RESULT_TEN)) {
                ticketResultResponseDto.getResultTenGroup().add(line);
            } else if (line.getResult().equals(RESULT_FIVE)) {
                ticketResultResponseDto.getResultFiveGroup().add(line);
            } else if (line.getResult().equals(RESULT_ONE)) {
                ticketResultResponseDto.getResultOneGroup().add(line);
            } else if (line.getResult().equals(RESULT_ZERO)) {
                ticketResultResponseDto.getResultZeroGroup().add(line);
            }
        }
    }

    public List<LineDto> generateRandomTicket(int numberOfLines){

        LineDto newLine;
        List<LineDto> generatedLines = new ArrayList<>();

        Random numberGenerator = new Random();

        while (generatedLines.size() < numberOfLines){
            newLine = new LineDto();
            newLine.setNumber_one(numberGenerator.nextInt(UPPER_BOUND));
            newLine.setNumber_two(numberGenerator.nextInt(UPPER_BOUND));
            newLine.setNumber_three(numberGenerator.nextInt(UPPER_BOUND));

            generatedLines.add(newLine);
        }


        return generatedLines;
    }

}
