package se.spargrisen.spargrisen.Repository;

import se.spargrisen.spargrisen.Account;
import se.spargrisen.spargrisen.Category;
import se.spargrisen.spargrisen.User;

import java.util.List;


public interface SpargrisenRepository {

    double deposit(double income, int id);
    List<Category> getCategories(int userId);
    List<Category> getAllCategories();
    Account getAccount(int userId);
    List<Transaction> getTransactions(int account_id);
    List<Budget> getBudgets(int user_id);
    User registerNewUser(String username, String password, String name);
    void registerNewAccount(int user_ID);

}
