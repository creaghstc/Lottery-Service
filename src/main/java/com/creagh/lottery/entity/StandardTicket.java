package com.creagh.lottery.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "STANDARD_TICKET")
@Getter
@Setter
public class StandardTicket {

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private int id;

    @Column(name = "STATUS_CHECKED")
    private String statusChecked;

    @OneToMany(mappedBy = "ticket", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private List<StandardLine> lines;
}
