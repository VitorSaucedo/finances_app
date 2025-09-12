package com.finances_app.entities;

import java.time.LocalDate;

public class Finance {
    private LocalDate date;
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
