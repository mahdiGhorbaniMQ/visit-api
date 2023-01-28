package com.example.visit.model;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity()
public class Visit {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter @Id
    private long id;

    @Getter @Setter
    private Date start;

    @Getter @Setter @ManyToOne
    private User user;

    public Visit() {}
    public Visit(
            Date start,
            User user
    ) {
        this.start = start;
        this.user = user;
    }
}
