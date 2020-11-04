package com.example.centrumtelefonii.models;


import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
public class Click {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int cid;

    @Column(unique=true)
    private LocalDate date;

    private int clicks;


    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }


    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getClicks() {
        return clicks;
    }

    public void setClicks(int clicks) {
        this.clicks = clicks;
    }
}