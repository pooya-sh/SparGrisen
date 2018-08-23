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

    public void withdraw(double amount) {

        if (balance >= amount)
            balance -= amount;
        else
            System.out.println("Balance is too low.");
    }



    public double getBalance() {
        return balance;
    }

}