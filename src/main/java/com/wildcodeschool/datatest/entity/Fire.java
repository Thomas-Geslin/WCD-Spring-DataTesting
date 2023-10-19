package com.wildcodeschool.datatest.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.*;

import java.time.Instant;

@Entity
public class Fire {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int severity;
    private Instant Date;

    @ManyToOne
    @JoinColumn(name = "fireman_id")
    private Fireman fireman;

    public Fire(int severity, Instant date) {
        this.severity = severity;
        Date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getSeverity() {
        return severity;
    }

    public void setSeverity(int severity) {
        this.severity = severity;
    }

    public Instant getDate() {
        return Date;
    }

    public void setDate(Instant date) {
        Date = date;
    }

    public Fireman getFireman() {
        return fireman;
    }

    public void setFireman(Fireman fireman) {
        this.fireman = fireman;
    }
}
