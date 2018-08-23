package se.spargrisen.spargrisen.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.spargrisen.spargrisen.*;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Component
public class JDBCSpargrisenRepository implements SpargrisenRepository {

    @Autowired
    private DataSource dataSource;


    @Override
    public List<Transaction> getTransactions(int account_ID) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "SELECT t.transaction_ID, t.account_ID, t.category_ID, " +
                             "t.transaction_date, t.ammount, t.description, c.name " +
                             "FROM transactions AS t " +
                             "INNER JOIN categories AS c ON t.category_ID = c.category_ID " +
                             "WHERE t.account_ID = ?")) {
            ps.setInt(1, account_ID);
            ResultSet rs = ps.executeQuery();
            List<Transaction> transactions = new ArrayList<>();
            while (rs.next()) {
                transactions.add(rsTransaction(rs));
            }
            if (transactions.size() > 0) {
                return transactions;
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new SpargrisenRepositoryExeption(e);
        }
    }

    @Override
    public List<Budget> getBudgets(int user_ID) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "SELECT b.budget_ID, b.category_ID, b.ammount, b.budget_date, c.name " +
                             "FROM budgets AS b " +
                             "INNER JOIN categories AS c ON b.category_ID = c.category_ID " +
                             "WHERE b.user_ID = ?")) {
            ps.setInt(1, user_ID);
            ResultSet rs = ps.executeQuery();
            List<Budget> budgets = new ArrayList<>();
            while (rs.next()) {
                budgets.add(rsBudget(rs));
            }
            if (budgets.size() > 0) {
                return budgets;
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new SpargrisenRepositoryExeption(e);
        }
    }


    @Override
    public double deposit(double income, int id) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT balance + ? as newbalance FROM accounts where account_id = ?")) {
            ps.setDouble(1, income);
            ps.setInt(2, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getDouble("newbalance");
            } else {
                throw new Exception("Didn't find balance.");
            }

        } catch (Exception e) {
            throw new SpargrisenRepositoryExeption(e);
        }

    }


    @Override
    public List<Category> getCategories(int userId) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT c.category_ID, c.name " +
                     " FROM categories c " +
                     " INNER JOIN users_categories AS uc " +
                     " ON c.category_ID = uc.category_ID" +
                     " WHERE uc.user_ID = ?")) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            List<Category> categories = new ArrayList<>();
            while (rs.next()) categories.add(rsCategory(rs));
            return categories;

        } catch (SQLException e) {
            throw new SpargrisenRepositoryExeption(e);
        }

    }

    @Override
    public List<Category> getAllCategories() {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT c.category_ID, c.name " +
                     " FROM categories c "
             )) {
            ResultSet rs = ps.executeQuery();
            List<Category> categories = new ArrayList<>();
            while (rs.next()) categories.add(rsCategory(rs));
            return categories;

        } catch (SQLException e) {
            throw new SpargrisenRepositoryExeption(e);
        }

    }

    @Override
    public Account getAccount(int user_ID) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT a.account_ID, a.balance FROM accounts a WHERE a.user_ID = ?")) {
            ps.setInt(1, user_ID);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) throw new SpargrisenRepositoryExeption("No account that matches the User ID");
            return rsAccount(rs);

        } catch (SQLException e) {
            throw new SpargrisenRepositoryExeption(e);
        }

    }


    private Category rsCategory(ResultSet rs) throws SQLException {
        return new Category(
                rs.getInt("category_id"),
                rs.getString("name")
        );

    }

    public User checkUsernamePassword(String username, String password) {
        try {
            Connection conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement("Select u.user_ID, u.name" +
                    " FROM users AS u WHERE u.username = ? AND u.password = ?;");
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rsUser(rs);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new SpargrisenRepositoryExeption(e);
        }
    }

    @Override
    public User registerNewUser(String username, String password, String name) {
        System.out.println("hej");
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement("INSERT INTO users (users.name, users.username, users.password)" +
                     " VALUES (?,?,?)")) {
            ps.setString(1, name);
            ps.setString(2, username);
            ps.setString(3, password);
            int rs = ps.executeUpdate();
            if (rs <= 0) {
                throw new SpargrisenRepositoryExeption("Could not create user");
            } else {
                User user = checkUsernamePassword(username, password);
                return user;
            }

        } catch (SQLException e) {
            throw new SpargrisenRepositoryExeption(e);
        }
    }

    @Override
    public void registerNewAccount(int user_ID) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement("INSERT INTO accounts (accounts.user_ID, accounts.balance)" +
                     " VALUES (?,?)")) {
            ps.setInt(1, user_ID);
            ps.setDouble(2, 0);
            int rs = ps.executeUpdate();
            if (rs <= 0) {
                throw new SpargrisenRepositoryExeption("Could not create account");
            }

        } catch (SQLException e) {
            throw new SpargrisenRepositoryExeption(e);
        }
    }

    @Override
    public boolean registerNewBudget(int user_ID, Budget budget) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement("INSERT INTO budgets (budgets.user_ID, budgets.ammount, " +
                     "budgets.budget_date, budgets.category_ID)" +
                     " VALUES (?,?,?,?)")) {
            ps.setInt(1, user_ID);
            ps.setDouble(2, budget.getAmmount());
            ps.setDate(3, Date.valueOf(budget.getBudget_date()));
            ps.setInt(4, budget.getCategory_ID());
            int rs = ps.executeUpdate();
            if (rs <= 0) {
                throw new SpargrisenRepositoryExeption("Could not create budget entry");
            } else {
                return true;
            }
        } catch (SQLException e) {
            throw new SpargrisenRepositoryExeption(e);
        }
    }

    @Override
    public boolean updateBudget(int user_ID, Budget budget) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement("UPDATE budgets " +
                     "SET ammount = ?, budget_date = ?, category_ID = ? " +
                     "WHERE budget_ID = ?")) {
            ps.setDouble(1, budget.getAmmount());
            ps.setDate(2, Date.valueOf(budget.getBudget_date()));
            ps.setInt(3, budget.getCategory_ID());
            ps.setInt(4, budget.getBudget_ID());
            int rs = ps.executeUpdate();
            if (rs <= 0) {
                throw new SpargrisenRepositoryExeption("Could not update budget entry");
            } else {
                return true;
            }
        } catch (SQLException e) {
            throw new SpargrisenRepositoryExeption(e);
        }
    }

    @Override
    public Budget budgetExist(LocalDate budget_date, int user_ID, int category_ID) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT *, c.name " +
                     "FROM budgets AS b " +
                     "INNER JOIN categories AS c ON b.category_ID = c.category_ID " +
                     "WHERE b.budget_date = ? AND b.user_ID = ? AND b.category_ID = ?")) {
            ps.setDate(1, Date.valueOf(budget_date));
            ps.setInt(2, user_ID);
            ps.setInt(3, category_ID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rsBudget(rs);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new SpargrisenRepositoryExeption(e);
        }
    }

    private User rsUser(ResultSet rs) throws SQLException {
        return new User(
                rs.getInt("User_ID"),
                rs.getString("name")
        );
    }

    private Account rsAccount(ResultSet rs) throws SQLException {
        return new Account(
                rs.getInt("account_ID"),
                rs.getDouble("balance")
        );
    }

    private Transaction rsTransaction(ResultSet rs) throws SQLException {
        return new Transaction(
                rs.getInt("transaction_ID"),
                rs.getInt("account_ID"),
                rs.getInt("category_ID"),
                rs.getDate("transaction_date").toLocalDate(),
                rs.getDouble("ammount"),
                rs.getString("description"),
                rs.getString("name")
        );
    }

    private Budget rsBudget(ResultSet rs) throws SQLException {
        return new Budget(
            rs.getInt("budget_ID"),
            rs.getInt("category_ID"),
            rs.getDouble("ammount"),
            rs.getDate("budget_date").toLocalDate(),
            rs.getString("name")
        );
    }
}





