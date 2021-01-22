package com.creagh.lotteryService.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class TicketResponseDto {

    private int id;
    private String status;
    private List<LineDto> lines;

}
