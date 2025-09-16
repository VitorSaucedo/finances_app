package com.finances_app.entities;

import jakarta.persistence.Entity;

import java.io.Serializable;
import java.time.LocalDate;

// @Entity marks this class as a JPA entity, which will be mapped to its own database table.
@Entity
public class Income extends Finance implements Serializable {
    // A unique ID for serialization, ensuring class compatibility.
    private static final long serialVersionUID = 1L;

    // A field specific to the Income class to describe the source of the income.
    private String source;

    // A no-argument constructor, required by JPA. 'super()' calls the parent constructor.
    public Income() {
        super();
    }

    // A parameterized constructor to create an Income instance with all its details.
    public Income(LocalDate date, double value, String description, String source) {
        // 'super()' initializes the inherited fields, ensuring the value is always positive.
        super(date, Math.abs(value), description);
        this.source = source;
    }

    // Overrides the setValue method from the parent Finance class.
    @Override
    public void setValue(double value) {
        // This logic ensures that income values are always stored as positive numbers.
        super.setValue(Math.abs(value));
    }

    // --- Standard Getters and Setters ---

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}