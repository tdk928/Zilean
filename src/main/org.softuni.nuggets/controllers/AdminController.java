package org.softuni.nuggets.controllers;

import org.modelmapper.ModelMapper;
import org.softuni.nuggets.entities.Employee;
import org.softuni.nuggets.models.binding.EditEmployeeBindingModel;
import org.softuni.nuggets.models.binding.RegisterBindingModel;
import org.softuni.nuggets.models.service.EmployeeServiceModel;
import org.softuni.nuggets.services.EmployeeService;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.cache.SpringCacheBasedUserCache;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class AdminController extends BaseController {

//    private SpringCacheBasedUserCache userCache;

    private EmployeeService employeeService;

    public AdminController(EmployeeService employeeService) {
//        this.userCache = cache;
        this.employeeService = employeeService;
    }

    @GetMapping("/register")
    public ModelAndView register(ModelAndView modelAndView, Model model) {
        if (!model.containsAttribute("employerInput")) {
            model.addAttribute("employerInput", new RegisterBindingModel());
        }

        return this.view("register");
    }

    @PostMapping("/register")
    public ModelAndView registerConfirm(@ModelAttribute RegisterBindingModel bindingModel) {
        if (bindingModel.getPassword().equals(bindingModel.getConfirmPassword())) {
            this.employeeService.register(bindingModel);
        }

        return this.redirect("login");
    }

    @GetMapping("/all-employers")
    public ModelAndView listAllEmployees() {
        return this.view("all-employers", "allEmployers", this.employeeService.getAllEmployers());

    }

    @GetMapping("/edit/{egn}")
    public ModelAndView editEmployee(@PathVariable("egn") String egn,Model model, ModelMapper modelMapper) {
        EmployeeServiceModel employeeByEgn = this.employeeService.getByEgn(egn);

        if (!model.containsAttribute("employerInput")) {
            EditEmployeeBindingModel bindingModel = modelMapper.map(employeeByEgn, EditEmployeeBindingModel.class);

            model.addAttribute("employerInput", bindingModel);
        }

        return this.view("edit-employer");
    }

    @PostMapping("/edit/{egn}")
    public ModelAndView editEmployerForm(@PathVariable String egn, @ModelAttribute(name = "employerInput") EditEmployeeBindingModel editEmployeeBindingModel,
                                         BindingResult bindingResult,RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.employerInput", bindingResult);
            redirectAttributes.addFlashAttribute("employerInput", editEmployeeBindingModel);

            return this.redirect("home");
        } else {
            this.employeeService.editEmployer(egn, editEmployeeBindingModel);
//            this.userCache.removeUserFromCache(egn);
            return this.redirect("all-employers");
        }


    }

    @GetMapping("/delete/{egn}")
    public ModelAndView deleteEmployer(@PathVariable String egn, ModelAndView modelAndView, Model model, ModelMapper modelMapper) {
        EmployeeServiceModel employeeByEgn = this.employeeService.getByEgn(egn);

        model.addAttribute("employerInput", modelMapper.map(employeeByEgn, EditEmployeeBindingModel.class)); //TODO fix with deleteBinding?

        return this.view("remove-employer");
    }

    @PostMapping("/delete/{egn}")
    public ModelAndView removeConfirm(@PathVariable String egn, ModelAndView modelAndView) {
        this.employeeService.removeEmployer(egn);

//        modelAndView.setViewName("redirect:/all-employers");


        return this.redirect("all-employers");
    }

    @PostMapping("/change-password")
    public ModelAndView changePassword(@ModelAttribute RegisterBindingModel bindingModel, ModelAndView modelAndView) {
        modelAndView.setViewName("change-password");

//        if (bindingModel.getPassword().equals(bindingModel.getConfirmPassword())) {
//            this.employeeService.register(bindingModel);
//        }

        return modelAndView;
    }
}
