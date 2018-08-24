package se.spargrisen.spargrisen;

import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import se.spargrisen.spargrisen.repository.JDBCSpargrisenRepository;

import javax.servlet.http.HttpSession;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

    @GetMapping("/register")
    public ModelAndView register() {
        return new ModelAndView("register");
    }

    @GetMapping("/test")
    public ModelAndView test() {
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
        if (user != null) {
            session.setAttribute("user_ID", user.getUser_ID());
            session.setAttribute("user_name", user.getName());
            int year = LocalDate.now().getYear();
            int month = LocalDate.now().getMonthValue();
            int day = LocalDate.now().getDayOfMonth();
            session.setAttribute("currentBudgetDate", LocalDate.of(year, month, day));
            session.setAttribute("chosenBudgetDate", LocalDate.of(year, month, 01));
            return new ModelAndView("redirect:homepage");
        }
        return new ModelAndView("login");
    }

    @GetMapping("/homepage")
    public ModelAndView homepage(HttpSession session) {
        Account account = repository.getAccount((int) session.getAttribute("user_ID"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate currentBudgetDate = LocalDate.parse(session.getAttribute("currentBudgetDate").toString(), formatter);
        LocalDate chosenBudgetDate = LocalDate.parse(session.getAttribute("chosenBudgetDate").toString(), formatter);
        System.out.println(chosenBudgetDate);
        List<Budget> budgets = repository.getBudgets((int) session.getAttribute("user_ID"), chosenBudgetDate);
        List<Category> categories = repository.getCategories((int) session.getAttribute("user_ID"));
        List<Transaction> transactions = repository.getTransactions(account.getAccountID());
        return new ModelAndView("homepage")
                .addObject("user_ID", session.getAttribute("user_ID"))
                .addObject("user_name", session.getAttribute("user_name"))
                .addObject("currentBudgetDate", currentBudgetDate)
                .addObject("chosenBudgetDate", chosenBudgetDate)
                .addObject("account", account)
                .addObject("budgets", budgets)
                .addObject("categories", categories)
                .addObject("transactions", transactions);
    }

    @PostMapping("/homepage/chooseBudgetDate")
    public ModelAndView updateBudgetListHomePage(HttpSession session, @RequestParam String budgetYear, @RequestParam String budgetMonth) {
        LocalDate chosenDate = parseChosenBudgetDate(budgetYear, budgetMonth);
        session.setAttribute("chosenBudgetDate", chosenDate);
        return new ModelAndView("redirect:/homepage");
    }

    @GetMapping("/budget")
    public ModelAndView budget(HttpSession session) {
        Account account = repository.getAccount((int) session.getAttribute("user_ID"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate currentBudgetDate = LocalDate.parse(session.getAttribute("currentBudgetDate").toString(), formatter);
        LocalDate chosenBudgetDate = LocalDate.parse(session.getAttribute("chosenBudgetDate").toString(), formatter);
        List<Category> categories = repository.getCategories((int) session.getAttribute("user_ID"));
        List<Budget> budgets = repository.getBudgets((int) session.getAttribute("user_ID"), chosenBudgetDate);
        return new ModelAndView("budget")
                .addObject("user_ID", session.getAttribute("user_ID"))
                .addObject("user_name", session.getAttribute("user_name"))
                .addObject("currentBudgetDate", currentBudgetDate)
                .addObject("chosenBudgetDate", chosenBudgetDate)
                .addObject("account", account)
                .addObject("categories", categories)
                .addObject("budgets", budgets);
    }

    @PostMapping("/budget/update")
    public ModelAndView updateBudget(HttpSession session, @RequestParam Map<String, String> allRequestParams) {
        List<Budget> budgetsToUpdate = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate chosenBudgetDate = LocalDate.parse(session.getAttribute("chosenBudgetDate").toString(), formatter);
        int userID = (int) session.getAttribute("user_ID");
        for (Map.Entry entry : allRequestParams.entrySet()) {
            int categoryID = Integer.parseInt(entry.getKey().toString());
            double ammount = Double.parseDouble(entry.getValue().toString());
            Budget budget = repository.budgetExist(chosenBudgetDate, userID, categoryID);
            if (budget != null) {
                budget.setAmmount(ammount);
                repository.updateBudget(userID, budget);
            } else {
                budget = new Budget(0, categoryID, ammount, chosenBudgetDate, "");
                repository.registerNewBudget(userID, budget);
            }
        }
        return new ModelAndView("redirect:/budget");
    }

    @PostMapping("/transaction")
    public ModelAndView transaction(@RequestParam int account, @RequestParam double ammount, @RequestParam LocalDate date, @RequestParam String description) {
        Transaction transaction = new Transaction(0, account, 1, date, ammount, description, "" );
        repository.registerNewTransaction(transaction);
        return new ModelAndView("redirect:homepage");
    }

    @PostMapping("/budget/chooseBudgetDate")
    public ModelAndView updateBudgetListBudgetPage(HttpSession session, @RequestParam String budgetYear, @RequestParam String budgetMonth) {
        LocalDate chosenDate = parseChosenBudgetDate(budgetYear, budgetMonth);
        session.setAttribute("chosenBudgetDate", chosenDate);
        return new ModelAndView("redirect:/budget");
    }

    @PostMapping("/register")
    public ModelAndView register(HttpSession session, @RequestParam String username, @RequestParam String password, @RequestParam String name) {
        User user = repository.registerNewUser(username, password, name);
        if (user != null) {
            repository.registerNewAccount(user.getUser_ID());
            for (int i = 1; i < 18; i++) {
                repository.registerStandardCategory(user.getUser_ID(),i);
            }
            session.setAttribute("user_ID", user.getUser_ID());
            session.setAttribute("user_name", user.getName());
            return new ModelAndView("redirect:homepage");
        }
        return new ModelAndView("login");
    }

    @PostMapping("/homepage/deleteTransaction")
    public ModelAndView deleteTransaction(@RequestParam int deleteButton) {
        repository.deleteTransaction(deleteButton);
        return new ModelAndView("redirect:/homepage");
    }
  
      private LocalDate parseChosenBudgetDate(String budgetYear, String budgetMonth) {
        int year = Integer.parseInt(budgetYear);
        int month = Integer.parseInt(budgetMonth);
        return LocalDate.of(year, month, 01);
    }

}
