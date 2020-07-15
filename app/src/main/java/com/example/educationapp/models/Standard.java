package com.example.educationapp.models;

import com.google.firebase.database.DataSnapshot;

import java.util.HashMap;
import java.util.List;

public class Standard {
    String id,name;

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


    public Standard() {
    }

    public Standard(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
