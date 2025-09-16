package com.finances_app.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

// Specifies that this is a superclass for other entities. Its fields will be mapped to the database columns of its subclasses, but it won't have its own table.
@MappedSuperclass
// Defines a base class for financial entries, containing common fields like ID, date, value, and description.
public abstract class Finance implements Serializable {
    // A unique ID for serialization, ensuring class compatibility during object persistence.
    private static final long serialVersionUID = 1L;

    // Marks this field as the primary key for the database table.
    @Id
    // Configures the primary key generation strategy to be auto-incremented by the database.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Formats the date as a string (e.g., "2025-09-15") when converting the object to JSON.
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate date;

    // Maps this field to a database column named "finance_value" and ensures it cannot be null.
    @Column(name = "finance_value", nullable = false)
    private double value;

    private String description;

    // A parameterized constructor for creating an instance with required fields.
    public Finance(LocalDate date, double value) {
        this.date = date;
        this.value = value;
    }

    // An overloaded constructor that includes the optional description field.
    public Finance(LocalDate date, double value, String description) {
        this.date = date;
        this.value = value;
        this.description = description;
    }

    // A no-argument constructor, which is required by JPA (Java Persistence API).
    public Finance() {
    }

    // --- Standard Getters and Setters ---

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}