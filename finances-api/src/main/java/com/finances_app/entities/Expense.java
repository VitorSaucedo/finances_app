package com.finances_app.entities;

import jakarta.persistence.Entity;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
// @Entity marks this class as a JPA entity, which will be mapped to its own database table.
public class Expense extends Finance {
    // A unique ID for serialization, ensuring class compatibility.
    private static final long serialVersionUID = 1L;

    // A field specific to the Expense class to categorize the expense.
    private String category;

    // A no-argument constructor, required by JPA. 'super()' calls the parent constructor.
    public Expense() {
        super();
    }

    // A parameterized constructor to create an Expense instance with all its details.
    public Expense(LocalDate date, double value, String description, String category) {
        // 'super()' initializes the fields inherited from the Finance class.
        super(date, value, description);
        this.category = category;
        // Ensures the expense value is stored correctly as a negative number.
        this.setValue(value);
    }

    // Overrides the setValue method from the parent Finance class.
    @Override
    public void setValue(double value) {
        // This logic ensures that expense values are always stored as negative numbers.
        super.setValue(Math.abs(value) * -1);
    }

    // --- Standard Getters and Setters ---

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}