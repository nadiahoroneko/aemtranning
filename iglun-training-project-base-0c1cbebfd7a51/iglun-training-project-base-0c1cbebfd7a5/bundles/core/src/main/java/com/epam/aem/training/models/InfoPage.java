package com.epam.aem.training.models;

public class InfoPage {
    private String title;
    private String decription;
    private String image;
    private String path;

    public InfoPage(String title, String decription, String image, String path) {
        this.title = title;
        this.decription = decription;
        this.image = image;
        this.path=path;
    }

    public String getTitle() {
        return title;
    }

    public String getDecription() {
        return decription;
    }

    public String getImage() {
        return image;
    }

    public String getPath() {
        return path;
    }
}
