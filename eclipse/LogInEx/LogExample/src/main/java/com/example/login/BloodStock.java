package com.example.login;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "BloodStock")
public class BloodStock {

    @Id
    @Column(name = "bloodgroup", nullable = false)
    private String bloodGroup; // Use consistent naming with DB

    @Column(nullable = false)
    private int quantity = 0; // Default value for quantity

    @Column
    private Date bestBefore;

    @Column
    private String status;

    // Getters and Setters
    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Date getBestBefore() {
        return bestBefore;
    }

    public void setBestBefore(Date bestBefore) {
        this.bestBefore = bestBefore;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
