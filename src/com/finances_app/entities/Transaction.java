package com.finances_app.entities;

import com.finances_app.enums.TransactionType;

public class Transaction {

    private Finance financeItem;
    private TransactionType type;

    public Transaction(Finance financeItem, TransactionType type) {
        this.financeItem = financeItem;
        this.type = type;
    }

    public Finance getFinanceItem() {
        return financeItem;
    }

    public void setFinanceItem(Finance financeItem) {
        this.financeItem = financeItem;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }
}
