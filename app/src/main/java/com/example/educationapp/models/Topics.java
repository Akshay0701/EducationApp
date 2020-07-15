package com.example.educationapp.models;

import java.io.Serializable;

public class Topics implements Serializable {

    String chapter,standard,name,pdfUrl,videoUrl,subject,type;

    public Topics() {
    }

    public Topics(String chapter, String standard, String name, String pdfUrl, String videoUrl, String subject, String type) {
        this.chapter = chapter;
        this.standard = standard;
        this.name = name;
        this.pdfUrl = pdfUrl;
        this.videoUrl = videoUrl;
        this.subject = subject;
        this.type = type;
    }

    public String getChapter() {
        return chapter;
    }

    public void setChapter(String chapter) {
        this.chapter = chapter;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPdfUrl() {
        return pdfUrl;
    }

    public void setPdfUrl(String pdfUrl) {
        this.pdfUrl = pdfUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }
}
