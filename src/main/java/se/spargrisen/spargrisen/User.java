package se.spargrisen.spargrisen;

public class User {
    private int user_ID;
    private String name;

    public User(int id, String name) {
        this.user_ID = id;
        this.name = name;
    }

    public int getUser_ID() {
        return user_ID;
    }

    public String getName() {
        return name;
    }
}
