package com.example.visit.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
public class WorkTime {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter @Id
    private long id;

    @Getter @Setter
    private Date start;

    @Getter @Setter
    private Date end;

    @Getter @Setter
    private Date period;

    public WorkTime() {}
    public WorkTime(
            Date start,
            Date end,
            Date period
    ) {
        this.start = start;
        this.end = end;
        this.period = period;
    }
}
