package se.spargrisen.spargrisen;

import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class SpargrisenController {

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
                .addObject("Categories", Categories.values());
    }

    @GetMapping("/budgetCategory")
    public ModelAndView budgetCategory() {
        return new ModelAndView("budgetmanagment");
    }

    @PostMapping("/budgetCategory")
    public ModelAndView budgetCategory(@RequestParam String partsum) {
        return new ModelAndView("budgetmanagment")
                .addObject("partsum", partsum);
    }
    


}
