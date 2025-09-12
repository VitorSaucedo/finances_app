package com.finances_app.ui;

import com.finances_app.entities.Expense;
import com.finances_app.entities.Finance;
import com.finances_app.entities.Income;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class ConsoleUI {

    private final Scanner scanner = new Scanner(System.in);
    private final DateTimeFormatter monthYearFormatter = DateTimeFormatter.ofPattern("MM/yyyy");

    public int displayMainMenuAndGetChoice() {
        System.out.println("\n------------------------------------");
        System.out.println("Choose an option:");
        System.out.println("1. Add Income");
        System.out.println("2. Add Expense");
        System.out.println("3. View Monthly Statement");
        System.out.println("4. Exit");
        System.out.print(">> ");
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public Income getNewIncomeDetails() {
        System.out.println("\n--- Add New Income ---");
        try {
            LocalDate date = getDateFromUserInput();
            System.out.print("Description: ");
            String description = scanner.nextLine();
            System.out.print("Value: ");
            double value = Double.parseDouble(scanner.nextLine());
            System.out.print("Source (e.g., Salary, Freelance): ");
            String source = scanner.nextLine();
            return new Income(date, value, description, source);
        } catch (NumberFormatException e) {
            System.err.println("Invalid value. Please enter a number.");
            return null;
        }
    }

    public Expense getNewExpenseDetails() {
        System.out.println("\n--- Add New Expense ---");
        try {
            LocalDate date = getDateFromUserInput();
            System.out.print("Description: ");
            String description = scanner.nextLine();
            System.out.print("Value: ");
            double value = Double.parseDouble(scanner.nextLine());
            System.out.print("Category (e.g., Food, Rent): ");
            String category = scanner.nextLine();
            return new Expense(date, value, description, category);
        } catch (NumberFormatException e) {
            System.err.println("Invalid value. Please enter a number.");
            return null;
        }
    }

    public YearMonth getYearMonthForStatement() {
        System.out.print("\nEnter the year and month to view (MM/yyyy): ");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");
        try {
            return YearMonth.parse(scanner.nextLine(), formatter);
        } catch (DateTimeParseException e) {
            System.err.println("Invalid format. Please use MM/yyyy.");
            return null;
        }
    }

    public void displayMonthlyStatement(String period, List<Income> incomes, List<Expense> expenses, double balance) {
        System.out.printf("\n--- Statement for %s ---%n", period);

        if (incomes.isEmpty() && expenses.isEmpty()) {
            System.out.println("No transactions found for this period.");
            return;
        }

        System.out.println("\n--- INCOMES ---");
        if (incomes.isEmpty()) {
            System.out.println("No incomes recorded for this period.");
        } else {
            incomes.forEach(this::printTransactionDetails);
        }

        System.out.println("\n--- EXPENSES ---");
        if (expenses.isEmpty()) {
            System.out.println("No expenses recorded for this period.");
        } else {
            expenses.forEach(this::printTransactionDetails);
        }

        System.out.println("\n------------------------------------");
        System.out.printf("End of Period Balance: R$ %.2f%n", balance);
        System.out.println("------------------------------------");
    }

    public void showMessage(String message) {
        System.out.println(message);
    }

    public void showErrorMessage(String message) {
        System.err.println(message);
    }

    private LocalDate getDateFromUserInput() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");
        while (true) {
            System.out.print("Month and Year (MM/yyyy): ");
            String dateInput = scanner.nextLine();
            try {
                YearMonth yearMonth = YearMonth.parse(dateInput, formatter);
                return yearMonth.atDay(1);
            } catch (DateTimeParseException e) {
                System.err.println("Invalid format. Please use MM/yyyy (e.g., 09/2025).");
            }
        }
    }

    private void printTransactionDetails(Finance transaction) {
        String formattedDate = transaction.getDate().format(monthYearFormatter);

        String type = (transaction instanceof Income) ? "INCOME" : "EXPENSE";
        String details = (transaction instanceof Income)
                ? "Source: " + ((Income) transaction).getSource()
                : "Category: " + ((Expense) transaction).getCategory();

        System.out.printf("[%s] Value: R$ %-10.2f | Description: %-25s | %s%n",
                formattedDate,
                transaction.getValue(),
                transaction.getDescription(),
                details);
    }
}