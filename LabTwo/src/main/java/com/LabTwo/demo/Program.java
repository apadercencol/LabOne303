package com.LabTwo.demo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Program")  // Explicitly specifies the table name
public class Program {

    @Id
    @Column(name = "programcode")
    private String programCode;

    @Column(name = "programname")
    private String programName;

    @Column(name = "duration")
    private Integer duration;

    @Column(name = "fee")
    private Double fee;

    @Column(name = "professor")
    private String professor;

    // Default constructor
    public Program() {
    }

    // Constructor
    public Program(String programCode, String programName, Integer duration, Double fee, String professor) {
        this.programCode = programCode;
        this.programName = programName;
        this.duration = duration;
        this.fee = fee;
        this.professor = professor;
    }

    // Getters and Setters
    public String getProgramCode() {
        return programCode;
    }

    public void setProgramCode(String programCode) {
        this.programCode = programCode;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }
}
