package com.finances_app.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

@MappedSuperclass
public abstract class Finance implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;
    @Column(name= "finance_value", nullable = false)
    private double value;
    private String description;

    public Finance(LocalDate date, double value) {
        this.date = date;
        this.value = value;
    }

    public Finance(LocalDate date, double value, String description) {
        this.date = date;
        this.value = value;
        this.description = description;
    }

    public Finance() {

    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
