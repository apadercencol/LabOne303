package com.example.login;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;




@Entity
@Table(name = "Donor")
public class Donor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long donorid;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String firstname;

    @Column(nullable = false)
    private String lastname;

    private String age_or_dob;
    private String gender;
    private String bloodgroup;
    private String city;
    private String phone;
    private String last_donation;
    private String scheduled_donation;

    @ManyToOne
    @JoinColumn(name = "bloodbankname")
    @JsonIgnore
    private BloodBank bloodBank;

    // Getters and Setters
    public Long getDonorid() {
        return donorid;
    }

    public void setDonorid(Long donorid) {
        this.donorid = donorid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getAge_or_dob() {
        return age_or_dob;
    }

    public void setAge_or_dob(String age_or_dob) {
        this.age_or_dob = age_or_dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBloodgroup() {
        return bloodgroup;
    }

    public void setBloodgroup(String bloodgroup) {
        this.bloodgroup = bloodgroup;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLast_donation() {
        return last_donation;
    }

    public void setLast_donation(String last_donation) {
        this.last_donation = last_donation;
    }

    public String getScheduled_donation() {
        return scheduled_donation;
    }

    public void setScheduled_donation(String scheduled_donation) {
        this.scheduled_donation = scheduled_donation;
    }

    public BloodBank getBloodBank() {
        return bloodBank;
    }

    public void setBloodBank(BloodBank bloodBank) {
        this.bloodBank = bloodBank;
    }
}