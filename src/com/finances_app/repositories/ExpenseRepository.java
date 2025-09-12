package com.finances_app.repositories;

import com.finances_app.entities.Expense;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ExpenseRepository {

    private static final String FILENAME = "expenses.dat";

    @SuppressWarnings("unchecked")
    public List<Expense> loadExpenses() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILENAME))) {
            return (List<Expense>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("Expenses file not found. Starting with an empty list.");
            return new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading expenses: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public void saveExpenses(List<Expense> expenses) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILENAME))) {
            oos.writeObject(expenses);
            System.out.println("Expenses saved successfully to " + FILENAME);
        } catch (IOException e) {
            System.err.println("Error saving expenses: " + e.getMessage());
            e.printStackTrace();
        }
    }
}