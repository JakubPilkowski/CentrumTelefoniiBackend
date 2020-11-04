package com.example.centrumtelefonii.models;


import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Product implements Serializable {
    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public enum Type {
        AKCESORIUM, TELEFON, ŻADEN
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int productid;

    @Column(length = 50)
    private String title;

    @Column(length = 250)
    private String description;

    private Type type;
    private int price;

    @ManyToOne
    private Image image;

    public int getProductid() {
        return productid;
    }

    public void setProductid(int imageid) {
        this.productid = imageid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String desc) {
        this.title = desc;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String longDesc) {
        this.description = longDesc;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

}
