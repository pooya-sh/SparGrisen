package se.spargrisen.spargrisen;

public class User {
    int user_ID;
    String name;

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
