package com.example.nike.models;

public class UserHelperCLass {
    String Fname;
    String Lname;
    String Email;
    String Password;
    String PhoneNumber;

    public UserHelperCLass() {
    }

    public UserHelperCLass(String fname, String lname, String email, String password, String phoneNumber) {
        Fname = fname;
        Lname = lname;
        Email = email;
        Password = password;
        PhoneNumber = phoneNumber;
    }

    public String getFname() {
        return Fname;
    }

    public void setFname(String fname) {
        Fname = fname;
    }

    public String getLname() {
        return Lname;
    }

    public void setLname(String lname) {
        Lname = lname;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }
}
