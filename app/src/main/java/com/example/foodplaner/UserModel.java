package com.example.foodplaner;

public class UserModel {
    private int id;
    private String firstName;
    private String lastName;
    private String gender;
    private String email;
    private String password;
    private String location;
    private boolean vegeterian;

    public UserModel(int id, String firstName, String lastName, String email, String password, String gender, String location, boolean vegeterian) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.location = location;
        this.vegeterian = vegeterian;
    }


    @Override
    public String toString() {
        return "UserModel{" +
                "id=" + id +
                ", name='" + firstName + '\'' +
                ", surname='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", gender='" + gender + '\'' +
                ", location='" + location + '\'' +
                ", vegeterian='" +vegeterian + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String name) {
        this.firstName = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String surname) {
        this.lastName = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
    public boolean isVegeterian() {
        return vegeterian;
    }

    public void setVegeterian(boolean vegeterian) {
        this.vegeterian = vegeterian;
    }
}
