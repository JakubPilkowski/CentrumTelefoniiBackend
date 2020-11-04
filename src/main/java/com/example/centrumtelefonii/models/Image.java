package com.example.centrumtelefonii.models;


import javax.persistence.*;

@Entity
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int imageid;


    private String webpUrl;
    private String pngUrl;
    private String pngThumbnailUrl;

    @Column(length = 50)
    private String alt;

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public int getImageid() {
        return imageid;
    }

    public void setImageid(int imageid) {
        this.imageid = imageid;
    }

    public String getWebpUrl() {
        return webpUrl;
    }

    public void setWebpUrl(String imageUrl) {
        this.webpUrl = imageUrl;
    }

    public String getPngUrl() {
        return pngUrl;
    }

    public void setPngUrl(String pngUrl) {
        this.pngUrl = pngUrl;
    }

    public String getPngThumbnailUrl() {
        return pngThumbnailUrl;
    }

    public void setPngThumbnailUrl(String pngThumbnailUrl) {
        this.pngThumbnailUrl = pngThumbnailUrl;
    }
}
