package org.softuni.nuggets.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController extends BaseController{
    @GetMapping("/")
    public ModelAndView index() {
       return this.view("index");
    }

    @GetMapping("/home")
    public ModelAndView home() {
       return this.view("home");
    }

    @GetMapping("/asd")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseBody
    public String asd() {
        return "VSICHKO GRUMNA!";
    }
}
