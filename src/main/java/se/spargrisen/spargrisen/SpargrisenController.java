package se.spargrisen.spargrisen;

import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import se.spargrisen.spargrisen.Repository.JDBCSpargrisenRepository;
import se.spargrisen.spargrisen.Repository.Transaction;
import javax.servlet.http.HttpSession;

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
        System.out.println(repository.getAccount(1).getBalance());
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
    @PostMapping("/login")
    public ModelAndView submit(HttpSession session, @RequestParam String inUsername, @RequestParam String inPassword) {
        User user = repository.checkUsernamePassword(inUsername, inPassword);
        System.out.println(user.name);
        return new ModelAndView("homepage")
                .addObject("name", user.name);
    }
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

        double income2 = Double.parseDouble(income);
        Account account = repository.getAccount(1);
        double newbalance = account.getBalance() + income2;

        Map<String, Integer> budgettable = new HashMap<>();
        budgettable.put("Boende", 5000);
        budgettable.put("Livsmedel", 2000);
        budgettable.put("Avgifter", 100);
        budgettable.put("Telefoni", 1500);
        budgettable.put("Nöje", 260);
        budgettable.put("Övrigt", 5400);

        return new ModelAndView("budgetmanagment")
                .addObject("income", newbalance)
                .addObject("Categories", repository.getAllCategories())
                .addObject("budgettable", budgettable);
    }

    @PostMapping("/budget/partofsum")
    public ModelAndView partofsum(@RequestParam String partsum) {

        return new ModelAndView("budgetmanagment");
    }


}
