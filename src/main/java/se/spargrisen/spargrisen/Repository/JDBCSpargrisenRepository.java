package se.spargrisen.spargrisen.Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;


@Component
public class JDBCSpargrisenRepository implements SpargrisenRepository {

    @Autowired
    DataSource dataSource;

    @Override
    public Map<Integer, String> getCategories() {
        try (Connection conn = dataSource.getConnection();
            Statement statement = conn.createStatement();
             ResultSet rs = statement.executeQuery("SELECT c.category_ID, c.name " +
                     " FROM categories c " +
                     " INNER JOIN "
                     " WHERE c.category_ID = ") {

             } catch (SQLException e) {

        }

    }

}


