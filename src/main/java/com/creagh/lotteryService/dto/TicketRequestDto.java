package com.creagh.lotteryService.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TicketRequestDto {

    List<LineDto> lines;
}
