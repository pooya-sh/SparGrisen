package se.spargrisen.spargrisen.Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;
import se.spargrisen.spargrisen.Account;
import se.spargrisen.spargrisen.Category;
import se.spargrisen.spargrisen.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public class JDBCSpargrisenRepository implements SpargrisenRepository {

    @Autowired
    private DataSource dataSource;


    //    @Override
//    public List<??> ??() {
//        try (Connection conn = ???.getConnection();
//             Statement stmt = conn.createStatement();
//             ResultSet rs = stmt.executeQuery("SELECT id, title FROM blogs")) {
//            List<Blog> blogs = new ArrayList<>();
//            while (rs.next()) blogs.add(rsBlog(rs));
//            return blogs;
//        } catch (SQLException e) {
//            throw new BlogRepositoryException(e);
//        }
//    }

    @Override
    public double deposit(double income, int id) {
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             PreparedStatement ps = conn.prepareStatement("SELECT balance + ? as newbalance FROM accounts where account_id = ?")) {
            ps.setDouble(1, income);
            ps.setInt(2, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getDouble("newbalance");
            }
            else {
                throw new Exception("Didn't find balance.");
            }

        } catch (Exception e) {
            throw new SpargrisenRepositoryExeption(e);
        }

    }


    @Override
    public List<Category> getCategories(int userId) {
        try (Connection conn = dataSource.getConnection();
             Statement statement = conn.createStatement();
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
             Statement statement = conn.createStatement();
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
    public Account getAccount(int userId) {
        try (Connection conn = dataSource.getConnection();
             Statement statement = conn.createStatement();
             PreparedStatement ps = conn.prepareStatement("SELECT a.account_ID, a.balance FROM accounts a WHERE a.user_ID = ?")) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) throw new SpargrisenRepositoryExeption("No account that matches ID: " + userId);
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

    public User checkUsernamePassword() {
        try {
            Connection conn = dataSource.getConnection();
            Statement statement = conn.createStatement();
            PreparedStatement ps = conn.prepareStatement("Select u.user_ID, u.name" +
                    " FROM users AS u WHERE u.username = ? AND u.password = ?;");
            //Parametrar
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return rsUser(rs);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new SpargrisenRepositoryExeption(e);
        }
    }

    private User rsUser(ResultSet rs) throws SQLException {
        return new User(
                rs.getInt("user_ID"),
                rs.getString("username")
        );
    }
    private Account rsAccount(ResultSet rs) throws SQLException {
        return new Account(
                rs.getInt("account_ID"),
                rs.getDouble("balance")
        );

    }

}





