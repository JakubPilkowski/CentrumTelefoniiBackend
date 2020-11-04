package com.example.centrumtelefonii.models;

import org.springframework.web.multipart.MultipartFile;

public class ImageUploadData {

    private MultipartFile webpForm;
    private MultipartFile pngForm;
    private String alt;

    public MultipartFile getWebpForm() {
        return webpForm;
    }

    public MultipartFile getPngForm() {
        return pngForm;
    }

    public String getAlt() {
        return alt;
    }

}
