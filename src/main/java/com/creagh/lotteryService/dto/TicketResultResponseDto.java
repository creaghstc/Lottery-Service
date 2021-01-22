package com.creagh.lotteryService.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class TicketResultResponseDto {

    int id;
    String status;
    List<LineResultDto> ResultTenGroup = new ArrayList<>();
    List<LineResultDto> ResultFiveGroup = new ArrayList<>();
    List<LineResultDto> ResultOneGroup = new ArrayList<>();
    List<LineResultDto> ResultZeroGroup = new ArrayList<>();

}
