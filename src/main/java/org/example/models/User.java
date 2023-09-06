package org.example.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;

public class User {
    private Long id;
    private String name;
    private String surname;
    private String father_name;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dob;
    private String sex;

    public User(){

    }
    public User(Long id, String name, String surname, String father_name, LocalDate DOB, String sex) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.father_name = father_name;
        this.dob = DOB;
        this.sex = sex;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getFather_name() {
        return father_name;
    }

    public void setFather_name(String father_name) {
        this.father_name = father_name;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
