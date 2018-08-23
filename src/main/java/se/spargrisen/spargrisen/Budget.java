package se.spargrisen.spargrisen;

import java.time.LocalDate;

public class Budget {
    private int budget_ID;
    private int category_ID;
    private double ammount;
    private LocalDate budget_date;
    private String category_description;

    public Budget(int budget_ID, int category_ID, double ammount, LocalDate budget_date, String category_description) {
        this.budget_ID = budget_ID;
        this.category_ID = category_ID;
        this.ammount = ammount;
        this.budget_date = budget_date;
        this.category_description = category_description;
    }

    public int getBudget_ID() {
        return budget_ID;
    }

    public int getCategory_ID() {
        return category_ID;
    }

    public double getAmmount() {
        return ammount;
    }

    public LocalDate getBudget_date() {
        return budget_date;
    }

    public String getCategory_description() {
        return category_description;
    }
}
