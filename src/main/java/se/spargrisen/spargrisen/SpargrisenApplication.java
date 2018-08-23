package se.spargrisen.spargrisen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import se.spargrisen.spargrisen.repository.JDBCSpargrisenRepository;

@SpringBootApplication
public class SpargrisenApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpargrisenApplication.class, args);

        JDBCSpargrisenRepository repository = new JDBCSpargrisenRepository();



    }
}
