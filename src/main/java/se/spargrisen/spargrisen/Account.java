package se.spargrisen.spargrisen;

import java.util.ArrayList;
import java.util.List;

public class Account {

    private String name;
    private double balance;
    public List<Integer> transactions = new ArrayList<>();

    public Account(String name, double balance) {
        this.name = name;
        this.balance = balance;
    }

    public void withdraw(double amount) {

        if (balance >= amount)
            balance -= amount;
        else
            System.out.println("Balance is too low.");
    }

    public double deposit (double income) {
        balance += income;
        return balance;
    }

    public double getBalance() {
        return balance;
    }

}