package com.example.hp.sqllite.model;

public class Student {
    private int ID;
    private String Name;
    private String Number;
    private String Email;
    private String Address;

    public Student() {
    }

    public Student(int ID, String name, String number, String email, String address) {
        this.ID = ID;
        Name = name;
        Number = number;
        Email = email;
        Address = address;
    }

    public Student(String name, String number, String email, String address) {
        Name = name;
        Number = number;
        Email = email;
        Address = address;
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return Name;
    }

    public String getNumber() {
        return Number;
    }

    public String getEmail() {
        return Email;
    }

    public String getAddress() {
        return Address;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setNumber(String number) {
        Number = number;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setAddress(String address) {
        Address = address;
    }

    @Override
    public String toString() {
        return "Student{" +
                "ID=" + ID +
                ", Name='" + Name + '\'' +
                ", Number='" + Number + '\'' +
                ", Email='" + Email + '\'' +
                ", Address='" + Address + '\'' +
                '}';
    }
}
