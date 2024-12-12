package com.example.login;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "BloodBank")
public class BloodBank {

    @Id
    private String bloodbankname;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String phone;

    private String website;

    private String email;

    @OneToMany(mappedBy = "bloodBank", cascade = CascadeType.ALL)
    private List<Donor> donors;

    // Getters and Setters
    public String getBloodbankname() {
        return bloodbankname;
    }

    public void setBloodbankname(String bloodbankname) {
        this.bloodbankname = bloodbankname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Donor> getDonors() {
        return donors;
    }

    public void setDonors(List<Donor> donors) {
        this.donors = donors;
    }
}
