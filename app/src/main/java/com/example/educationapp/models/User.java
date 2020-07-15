package com.example.educationapp.models;

public class User {
    String uid,name,area,phone,dateofbrith;

    public User() {
    }

    public User(String uid, String name, String area, String phone, String dateofbrith) {
        this.uid = uid;
        this.name = name;
        this.area = area;
        this.phone = phone;
        this.dateofbrith = dateofbrith;
    }


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDateofbrith() {
        return dateofbrith;
    }

    public void setDateofbrith(String dateofbrith) {
        this.dateofbrith = dateofbrith;
    }

}
