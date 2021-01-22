package com.creagh.lotteryService.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LineResultDto implements Comparable<LineResultDto> {

    private int number_one;
    private int number_two;
    private int number_three;
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