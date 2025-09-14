package com.finances_app.entities;

import jakarta.persistence.Entity;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
public class Income extends Finance implements Serializable {
    private static final long serialVersionUID = 1L;

    private String source;

    public Income() {
        super();
    }

    public Income(LocalDate date, double value, String description, String source) {
        super(date, Math.abs(value), description);
        this.source = source;
    }

    @Override
    public void setValue(double value) {
        super.setValue(Math.abs(value));
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}

