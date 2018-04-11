package org.softuni.nuggets.areas.user.controllers;

import org.softuni.nuggets.areas.user.services.EmployeeService;
import org.softuni.nuggets.controllers.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AccountController extends BaseController {
    private final EmployeeService userService;

    @Autowired
    public AccountController(EmployeeService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public ModelAndView login(@RequestParam(required = false, name = "error") String error) {
       if (error != null) {
           this.view("login","error", error);
       }

        return this.view("login");
    }

    @PostMapping("/logout")
    public ModelAndView logout(@RequestParam(required = false, name = "logout") String logout,RedirectAttributes redirectAttributes) {
        if (logout != null){
            redirectAttributes.addFlashAttribute("logout", logout);
        }

        return this.redirect("login");
    }

    @GetMapping("/changePassword")
    public ModelAndView changePassword() {
        return this.view("test");
    }

    @GetMapping("/calendar")
    public ModelAndView calendar() {
        return this.view("calendar");
    }

//    @GetMapping("/calendar")
//        public ModelAndView calendar() {
//           return this.view("calendar");
//        }

}
