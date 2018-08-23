package se.spargrisen.spargrisen;

import java.time.LocalDate;

public class Transaction {
    private int transaction_ID;
    private int account_ID;
    private int category_ID;
    private LocalDate transaction_date;
    private double ammount;
    private String description;
    private String category_description;

    public Transaction(int transaction_ID, int account_ID, int category_ID, LocalDate transaction_date,
                       double ammount, String description, String category_description) {
        this.transaction_ID = transaction_ID;
        this.account_ID = account_ID;
        this.category_ID = category_ID;
        this.transaction_date = transaction_date;
        this.ammount = ammount;
        this.description = description;
        this.category_description = category_description;
    }

    public int getTransaction_ID() {
        return transaction_ID;
    }

    public int getAccount_ID() {
        return account_ID;
    }

    public int getCategory_ID() {
        return category_ID;
    }

    public LocalDate getTransaction_date() {
        return transaction_date;
    }

    public double getAmmount() {
        return ammount;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory_description() {
        return category_description;
    }
}
