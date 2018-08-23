package se.spargrisen.spargrisen.Repository;

public class Budget {
    private int budget_ID;
    private int category_ID;
    private double ammount;
    private String category_description;

    public Budget(int budget_ID, int category_ID, double ammount, String category_description) {
        this.budget_ID = budget_ID;
        this.category_ID = category_ID;
        this.ammount = ammount;
        this.category_description = category_description;
    }
}
