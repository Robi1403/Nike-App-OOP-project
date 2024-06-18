package com.example.nike.models;

public class ProfileModel {
    String fname;
    String lname;
    String email;
    String phoneNumber;

    public ProfileModel(String fname, String lname, String email, String phoneNumber) {
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public ProfileModel() {
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
