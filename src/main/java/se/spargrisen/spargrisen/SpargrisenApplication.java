package se.spargrisen.spargrisen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import se.spargrisen.spargrisen.Repository.JDBCSpargrisenRepository;
import se.spargrisen.spargrisen.Repository.SpargrisenRepository;

import java.util.List;

@SpringBootApplication
public class SpargrisenApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpargrisenApplication.class, args);

        JDBCSpargrisenRepository repository = new JDBCSpargrisenRepository();



    }
}
