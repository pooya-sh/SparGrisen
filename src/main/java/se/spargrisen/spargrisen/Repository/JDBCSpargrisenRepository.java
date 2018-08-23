package se.spargrisen.spargrisen.Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
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
             PreparedStatement ps = conn.prepareStatement("SELECT balance + income FROM accounts where account_id= ?")){
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

        } catch (SQLException e) {
            throw new SpargrisenRepositoryExeption(e);
        }
    }


//    @Override
//    public Map<Integer, String> getCategories() {
//        try (Connection conn = dataSource.getConnection();
//            Statement statement = conn.createStatement();
//             ResultSet rs = statement.executeQuery("SELECT c.category_ID, c.name " +
//                     " FROM categories c " +
//                     " INNER JOIN users_categories AS uc " +
//                     " ON c.category_ID = uc.category_ID" +
//                     " WHERE uc.user_ID = ?");
//
//             } catch (SQLException e) {
//
//        }
//
//    }

}





