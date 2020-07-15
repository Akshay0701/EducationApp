package com.example.educationapp.models;

import java.io.Serializable;
import java.util.List;

public class Chapter implements Serializable {
    String standard ,name,subject;

    public Chapter(String standard, String name, String subject) {
        this.standard = standard;
        this.name = name;
        this.subject = subject;
    }

    public Chapter() {
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
