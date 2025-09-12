package com.finances_app.repositories;

import com.finances_app.entities.Income; // Import the Income class

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class IncomeRepository {
    private static final String FILENAME = "incomes.dat";

    @SuppressWarnings("unchecked")
    public List<Income> loadIncomes() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILENAME))) {
            return (List<Income>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("Incomes file not found. Starting with an empty list.");
            return new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading incomes: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public void saveIncomes(List<Income> incomes) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILENAME))) {
            oos.writeObject(incomes);
            System.out.println("Incomes saved successfully to " + FILENAME);
        } catch (IOException e) {
            System.err.println("Error saving incomes: " + e.getMessage());
            e.printStackTrace();
        }
    }
}