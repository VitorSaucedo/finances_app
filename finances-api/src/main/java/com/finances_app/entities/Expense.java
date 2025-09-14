package com.finances_app.entities;

import jakarta.persistence.Entity;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
public class Expense extends Finance {
    private static final long serialVersionUID = 1L;

    private String category;

    public Expense() {
        super();
    }

    public Expense(LocalDate date, double value, String description, String category) {
        super(date, value, description);
        this.category = category;
        this.setValue(value);
    }

    @Override
    public void setValue(double value) {
        super.setValue(Math.abs(value) * -1);
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
