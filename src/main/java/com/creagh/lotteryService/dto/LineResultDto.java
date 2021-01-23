package com.creagh.lotteryService.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LineResultDto implements Comparable<LineResultDto> {

    private int numberOne;
    private int numberTwo;
    private int numberThree;
    private String result;

    @Override
    public int compareTo(LineResultDto lineResultDto) {

        if (!this.result.trim().equals("") && !lineResultDto.getResult().trim().equals("")) {

            if (Integer.valueOf(lineResultDto.getResult()) < Integer.valueOf(this.result)) {
                return 1;
            } else if (Integer.valueOf(lineResultDto.getResult()) > Integer.valueOf(this.result)) {
                return -1;
            }
        }
        return 0;
    }
}