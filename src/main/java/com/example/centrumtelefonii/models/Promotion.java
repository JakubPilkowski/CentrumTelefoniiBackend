package com.example.centrumtelefonii.models;


import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Promotion {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int pid;
    private LocalDate date;
    private String day;

    @ManyToOne
    private Image image;


    private int price;

    @Column(length = 5)
    private int sale;

    @Column(length = 50)
    private String title;

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image imgUrl) {
        this.image = imgUrl;
    }

    public int getSale() {
        return sale;
    }

    public void setSale(int sale) {
        this.sale = sale;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }


    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
