package com.finances_app.entities;

import java.time.LocalDate;

public class Income extends Finance {

    private String source;

    public Income(LocalDate date, double value, String description, String source) {
        super(date, value, description);
        this.source = source;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}

