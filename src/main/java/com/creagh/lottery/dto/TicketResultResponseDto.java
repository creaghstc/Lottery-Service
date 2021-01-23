package com.creagh.lottery.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class TicketResultResponseDto {

    int id;
    String status;
    List<LineResultDto> resultGroup10 = new ArrayList<>();
    List<LineResultDto> resultGroup5 = new ArrayList<>();
    List<LineResultDto> resultGroup1 = new ArrayList<>();
    List<LineResultDto> resultGroup0 = new ArrayList<>();
}
