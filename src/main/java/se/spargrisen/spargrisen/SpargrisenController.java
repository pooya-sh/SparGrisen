package se.spargrisen.spargrisen;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import se.spargrisen.spargrisen.Repository.JDBCSpargrisenRepository;

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
//    @GetMapping("/homepage")
//    public ModelAndView homepage(HttpSession session) {
////session.setAttribute("user", );
//        if (session.getAttribute("user") != null) {
//            return new ModelAndView("homepage");
//        }
//        return new ModelAndView("login");
//    }
//    public static void main(String[] args) {
//        SpringApplication.run(SpargrisenController.class, args);
//    }

    @GetMapping("/budget")
    public ModelAndView budget() {
        return new ModelAndView("budgetmanagment");
    }

    @PostMapping("/budget")
    public ModelAndView budget(@RequestParam String income) {

        return new ModelAndView("budgetmanagment")
                .addObject("income", income)
                .addObject("Categories", repository.getAllCategories());
    }

    @PostMapping("/budget/partofsum")
    public ModelAndView partofsum(@RequestParam String partsum) {

        return new ModelAndView("budgetmanagment");
    }
}
