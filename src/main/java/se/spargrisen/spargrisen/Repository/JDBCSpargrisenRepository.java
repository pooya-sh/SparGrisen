package se.spargrisen.spargrisen.Repository;

import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

@Component
public class JDBCSpargrisenRepository implements SpargrisenRepository {

    @Override
    public List<??> ??() {
        try (Connection conn = ???.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT id, title FROM blogs")) {
            List<Blog> blogs = new ArrayList<>();
            while (rs.next()) blogs.add(rsBlog(rs));
            return blogs;
        } catch (SQLException e) {
            throw new BlogRepositoryException(e);
        }
    }

}
