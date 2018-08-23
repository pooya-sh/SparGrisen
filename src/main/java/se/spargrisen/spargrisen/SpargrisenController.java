package se.spargrisen.spargrisen;

import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import se.spargrisen.spargrisen.Repository.JDBCSpargrisenRepository;
import se.spargrisen.spargrisen.Repository.Transaction;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class SpargrisenController {

    @Autowired
    private JDBCSpargrisenRepository repository;

    @GetMapping("/login")
    public ModelAndView login() {
        return new ModelAndView("login");
    }

    //    @GetMapping("/logout")
//    public String logout(HttpSession session, HttpServletResponse res) {
//        session.invalidate();
//        Cookie cookie = new Cookie("JSESSIONID", "");
//        cookie.setMaxAge(0);
//        res.addCookie(cookie);
//        return "login";
//    }
//
//    @PostMapping("/login")
//    public String submit(HttpSession session, @RequestParam String username, @RequestParam String password) {
//        if (username.equalsIgnoreCase("") && password.equalsIgnoreCase("") ) {
//            session.setAttribute("user", username);
//            return "homepage";
//        }
//        return "login";
//    }
//
    @GetMapping("/homepage")
    public ModelAndView homepage() {
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction(1, 1, 5,
                LocalDate.of(2018, 8, 21), 500, "", "Mat"));
        transactions.add(new Transaction(1, 1, 5,
                LocalDate.of(2018, 8, 21), 550, "","Mat"));
        transactions.add(new Transaction(1, 1, 5,
                LocalDate.of(2018, 8, 21), 900, "Tessst","Mat"));
        transactions.add(new Transaction(1, 1, 5,
                LocalDate.of(2018, 8, 21), 501, "","Mat"));
        transactions.add(new Transaction(1, 1, 5,
                LocalDate.of(2018, 8, 21), 728, "Också test","Mat"));

        return new ModelAndView("homepage")
                .addObject("transactions", transactions);
    }

    @GetMapping("/budget")
    public ModelAndView budget() {
        return new ModelAndView("budgetmanagment");
    }

    @PostMapping("/budget")
    public ModelAndView budget(@RequestParam String income) {

        return new ModelAndView("budgetmanagment")
                .addObject("income", income)
                .addObject("Categories", Categories.values());
    }

    @PostMapping("/budget/partofsum")
    public ModelAndView partofsum(@RequestParam String partsum) {

        return new ModelAndView("budgetmanagment");

    }
}
