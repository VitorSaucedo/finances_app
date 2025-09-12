package com.finances_app.entities;

import java.io.Serializable;
import java.time.LocalDate;

public class Expense extends Finance {
    private static final long serialVersionUID = 1L;

    private String category;

    public Expense(LocalDate date, double value, String description, String category) {
        super(date, Math.abs(value) * -1, description);
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
