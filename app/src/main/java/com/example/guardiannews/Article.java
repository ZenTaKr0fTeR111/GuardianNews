package com.example.guardiannews;

public class Article {
    private final String title;
    private final String sectionName;
    private final String dateAndTime;
    private final String url;

    public Article(String title, String sectionName, String dateAndTime, String url) {
        this.title = title;
        this.sectionName = sectionName;
        this.dateAndTime = dateAndTime;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public String getSectionName() {
        return sectionName;
    }

    public String getDateAndTime() {
        return dateAndTime;
    }

    public String getUrl() {
        return url;
    }
}