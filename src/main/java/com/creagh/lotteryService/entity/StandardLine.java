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
    private int numberOne;
    @Column(name = "NUMBER_TWO")
    private int numberTwo;
    @Column(name = "NUMBER_THREE")
    private int numberThree;
    @Column(name = "RESULT")
    private String result = "";

    public StandardLine(int numberOne, int numberTwo, int numberThree) {
        this.numberOne = numberOne;
        this.numberTwo = numberTwo;
        this.numberThree = numberThree;
    }

}
