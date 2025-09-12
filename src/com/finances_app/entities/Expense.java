package com.finances_app.entities;

import java.time.LocalDate;

public class Expense extends Finance {

    private String category;

    public Expense(LocalDate date, double value, String description, String category) {
        super(date, value, description);
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
