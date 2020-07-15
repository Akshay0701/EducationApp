package com.example.educationapp.models;

public class Subject {
    String grade,id,name;

    public Subject() {
    }

    public Subject(String grade, String id, String name) {
        this.grade = grade;
        this.id = id;
        this.name = name;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
