package se.spargrisen.spargrisen;

public class Category {
    public final int ID;
    public final String NAME;

    public Category(int ID, String NAME) {
        this.ID = ID;
        this.NAME = NAME;

    }

    public int getID() {
        return ID;
    }

    public String getNAME() {
        return NAME;
    }
}