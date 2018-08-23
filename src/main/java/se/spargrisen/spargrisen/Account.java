package se.spargrisen.spargrisen;

import java.util.ArrayList;
import java.util.List;

public class Account {

    private int accountID;
    private double balance;
    public List<Integer> transactions = new ArrayList<>();

    public Account(){}
    public Account(int accountID, double balance) {
        this.accountID = accountID;
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }

    public int getAccountID() {
        return accountID;
    }
}