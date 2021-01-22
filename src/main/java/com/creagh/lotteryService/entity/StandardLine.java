package com.creagh.lotteryService.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "STANDARD_LINE")
@Getter
@Setter
@NoArgsConstructor
public class StandardLine {

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "TICKET_ID")
    private StandardTicket ticket;

    @Column(name = "NUMBER_ONE")
    private int number_one;
    @Column(name = "NUMBER_TWO")
    private int number_two;
    @Column(name = "NUMBER_THREE")
    private int number_three;
    @Column(name = "RESULT")
    private String result = "";

    public StandardLine(int number_one, int number_two, int number_three) {
        this.number_one = number_one;
        this.number_two = number_two;
        this.number_three = number_three;
    }

}
