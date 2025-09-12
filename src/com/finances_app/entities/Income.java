package com.finances_app.entities;

import java.io.Serializable;
import java.time.LocalDate;

public class Income extends Finance implements Serializable {
    private static final long serialVersionUID = 1L;

    private String source;

    public Income(LocalDate date, double value, String description, String source) {
        super(date, Math.abs(value), description);
        this.source = source;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}

