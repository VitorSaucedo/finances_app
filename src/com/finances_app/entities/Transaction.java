package com.finances_app.entities;

import com.finances_app.enums.TransactionType;

public class Transaction {
    private Finance financeItem;

    public Transaction(Finance financeItem) {
        if (!(financeItem instanceof Income || financeItem instanceof Expense)) {
            throw new IllegalArgumentException("The finance item must be an instance of Income or Expense.");
        }
        this.financeItem = financeItem;
    }

    public Finance getFinanceItem() {
        return financeItem;
    }

    public void setFinanceItem(Finance financeItem) {
        this.financeItem = financeItem;
    }

    public TransactionType getType() {
        if (this.financeItem instanceof Income) {
            return TransactionType.INCOME;
        } else {
            return TransactionType.EXPENSE;
        }
    }
}