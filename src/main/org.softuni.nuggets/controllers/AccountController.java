package org.softuni.nuggets.controllers;

import org.softuni.nuggets.models.binding.RegisterBindingModel;
import org.softuni.nuggets.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public ModelAndView changePassword(ModelAndView modelAndView) {
        return this.view("change-password");
    }

}
