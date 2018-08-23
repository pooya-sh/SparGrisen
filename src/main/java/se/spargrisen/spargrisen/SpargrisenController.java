package se.spargrisen.spargrisen;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import se.spargrisen.spargrisen.Repository.JDBCSpargrisenRepository;
import se.spargrisen.spargrisen.Repository.Transaction;
import javax.servlet.http.HttpSession;

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

    @GetMapping("/register")
    public ModelAndView register() {
        return new ModelAndView("register");
    }

    @GetMapping("/test")
    public ModelAndView test(){
        List<Transaction> transactions = repository.getTransactions(1);
        System.out.println(transactions.size());
        return new ModelAndView("test")
                .addObject("transactions", transactions);
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
        if(user != null){
            session.setAttribute("user_ID", user.getUser_ID());
            session.setAttribute("user_name", user.getName());
            return new ModelAndView("redirect:homepage");
        }
        return new ModelAndView("login");
    }

    @GetMapping("/homepage")
    public ModelAndView homepage(HttpSession session) {
        Account account = repository.getAccount((int)session.getAttribute("user_ID"));
        List<Transaction> transactions = repository.getTransactions(account.getAccountID());
        return new ModelAndView("homepage")
                .addObject("user_ID",session.getAttribute("user_ID"))
                .addObject("user_name",session.getAttribute("user_name"))
                .addObject("transactions", transactions);
    }

    @GetMapping("/budget")
    public ModelAndView budget() {
        return new ModelAndView("budget");
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

        return new ModelAndView("budget")
                .addObject("income", newbalance)
                .addObject("Categories", repository.getAllCategories())
                .addObject("budgettable", budgettable);
    }

    @PostMapping("/budget/partofsum")
    public ModelAndView partofsum(@RequestParam String partsum) {

        return new ModelAndView("budget");
    }

    @PostMapping("/register")
    public ModelAndView register(HttpSession session, @RequestParam String username, @RequestParam String password, @RequestParam String name) {
        User user = repository.registerNewUser(username, password, name);
        if(user != null) {
            repository.registerNewAccount(user.getUser_ID());
            session.setAttribute("user_ID", user.getUser_ID());
            session.setAttribute("user_name", user.getName());
            return new ModelAndView("redirect:homepage");
        }
        return new ModelAndView("login");
    }



}
