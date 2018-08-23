package se.spargrisen.spargrisen.Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.spargrisen.spargrisen.Category;

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
    public void deposit(double income, int id) {
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             PreparedStatement ps = conn.prepareStatement("SELECT balance + income FROM accounts where account_id= ?")) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

        } catch (SQLException e) {
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

    private Category rsCategory(ResultSet rs) throws SQLException {
        return new Category(
                rs.getInt("category_id"),
                rs.getString("name")
        );

    }

}





