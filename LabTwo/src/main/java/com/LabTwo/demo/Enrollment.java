package com.LabTwo.demo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.util.Date;

@Entity
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "applicationno")
    private Integer applicationNo;

    // Many-to-One relationship with Student
    @ManyToOne
    @JoinColumn(name = "studentid", referencedColumnName = "studentid")
    private Student student;

    // Many-to-One relationship with Program
    @ManyToOne
    @JoinColumn(name = "programcode", referencedColumnName = "programcode")
    private Program program;

    @Column(name = "startdate")
    private Date startDate;

    @Column(name = "amountpaid")
    private Double amountPaid;

    // Default constructor
    public Enrollment() {
    }

    // Constructor without applicationNo (as it will be auto-generated)
    public Enrollment(Student student, Program program, Date startDate, Double amountPaid) {
        this.student = student;
        this.program = program;
        this.startDate = startDate;
        this.amountPaid = amountPaid;
    }

    // Getters and Setters
    public Integer getApplicationNo() {
        return applicationNo;
    }

    public void setApplicationNo(Integer applicationNo) {
        this.applicationNo = applicationNo;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Program getProgram() {
        return program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Double getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(Double amountPaid) {
        this.amountPaid = amountPaid;
    }
}
