package org.softuni.nuggets.areas.user.controllers;


import org.softuni.nuggets.areas.admin.repositories.EventRepository;
import org.softuni.nuggets.areas.user.services.EmployeeService;
import org.softuni.nuggets.controllers.BaseController;
import org.softuni.nuggets.entities.Event;
import org.softuni.nuggets.models.binding.UserEditEmployeeBindingModel;
import org.softuni.nuggets.models.service.EmployeeServiceModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.Date;

@Controller
public class AccountController extends BaseController {
    private final EmployeeService userService;
    private final EventRepository eventRepository;

    @Autowired
    public AccountController(EmployeeService userService, EventRepository eventRepository) {
        this.userService = userService;
        this.eventRepository = eventRepository;
    }

    @GetMapping("/login")
    public ModelAndView login(@RequestParam(required = false, name = "error") String error) {
        if (error != null) {
            this.view("login", "error", error);
        }

        return this.view("login");
    }

    @PostMapping("/logout")
    public ModelAndView logout(@RequestParam(required = false, name = "logout") String logout, RedirectAttributes redirectAttributes) {
        if (logout != null) {
            redirectAttributes.addFlashAttribute("logout", logout);
        }

        return this.redirect("login");
    }

    @GetMapping("/changePassword")
    public ModelAndView editEmployer(Principal principal) {
        EmployeeServiceModel employer = this.userService.getByUsernameAndDeletedOnIsNotNull(principal.getName());
        return this.view("user/change-password").addObject("employer",employer);
    }

    @PostMapping("/changePassword")
    public ModelAndView editEmployer(UserEditEmployeeBindingModel model,Principal principal) {

        Authentication request = new UsernamePasswordAuthenticationToken(
                model.getUsername(),
                model.getPassword());

//            Authentication result = authenticationManager.authenticate(request);
        SecurityContextHolder.getContext().setAuthentication(request);
        EmployeeServiceModel employer = this.userService.getByUsernameAndDeletedOnIsNotNull(principal.getName());

        this.userService.editEmployer(employer.getUsername(),model);
        return this.redirect("home");
    }


    @GetMapping("user/calendar")
    public ModelAndView calendar() {
        return this.view("calendar");
    }

    @GetMapping("/test")
    public ModelAndView test() {
//        throw new ResourceNotFoundException();
        return this.view("test");
    }

}
