package se.spargrisen.spargrisen.repository;

import se.spargrisen.spargrisen.*;

import java.time.LocalDate;
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
    boolean registerNewBudget(int user_ID, Budget budget);
    boolean updateBudget(int user_ID, Budget budget);
    Budget budgetExist(LocalDate budget_date, int user_ID, int category_ID);
}
