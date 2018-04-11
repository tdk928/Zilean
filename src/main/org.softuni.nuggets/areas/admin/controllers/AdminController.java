package org.softuni.nuggets.areas.admin.controllers;

import org.modelmapper.ModelMapper;
import org.softuni.nuggets.areas.admin.services.AdminService;
import org.softuni.nuggets.controllers.BaseController;
import org.softuni.nuggets.models.binding.EditEmployeeBindingModel;
import org.softuni.nuggets.models.binding.RegisterBindingModel;
import org.softuni.nuggets.models.service.EmployeeServiceModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AdminController extends BaseController {

    private AdminService adminService;

    public AdminController(AdminService adminService) {

        this.adminService = adminService;
    }

    @GetMapping("/register")
    public ModelAndView register(Model model) {
        if (!model.containsAttribute("employerInput")) {
            model.addAttribute("employerInput", new RegisterBindingModel());
        }

        return this.view("register");
    }

    @PostMapping("/register")
    public ModelAndView registerConfirm(@ModelAttribute RegisterBindingModel bindingModel) {
        if (bindingModel.getPassword().equals(bindingModel.getConfirmPassword())) {
            this.adminService.register(bindingModel);
        }

        return this.redirect("login");
    }

    @GetMapping("/all-employers")
    public ModelAndView listAllEmployees() {
        return this.view("all-employers", "allEmployers", this.adminService.getAllEmployers());

    }

    @GetMapping("/edit/{username}")
    public ModelAndView editEmployee(@PathVariable("username") String username,Model model, ModelMapper modelMapper) {
        EmployeeServiceModel employeeByUsername = this.adminService.getByUsername(username);

        if (!model.containsAttribute("employerInput")) {
            EditEmployeeBindingModel bindingModel = modelMapper.map(employeeByUsername, EditEmployeeBindingModel.class);

            model.addAttribute("employerInput", bindingModel);
        }

        return this.view("edit-employer");
    }

    @PostMapping("/edit/{username}")
    public ModelAndView editEmployerForm(@PathVariable String username, @ModelAttribute(name = "employerInput") EditEmployeeBindingModel editEmployeeBindingModel,
                                         BindingResult bindingResult,RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.employerInput", bindingResult);
            redirectAttributes.addFlashAttribute("employerInput", editEmployeeBindingModel);

            return this.redirect("home");
        } else {
            this.adminService.editEmployer(username, editEmployeeBindingModel);
//            this.userCache.removeUserFromCache(egn);
            return this.redirect("all-employers");
        }


    }

    @GetMapping("/delete/{username}")
    public ModelAndView deleteEmployer(@PathVariable String username, Model model, ModelMapper modelMapper) {
        EmployeeServiceModel employeeByUsername = this.adminService.getByUsername(username);

        model.addAttribute("employerInput", modelMapper.map(employeeByUsername, EditEmployeeBindingModel.class)); //TODO fix with deleteBinding?

        return this.view("remove-employer");
    }

    @PostMapping("/delete/{username}")
    public ModelAndView removeConfirm(@PathVariable String username) {
        this.adminService.removeEmployer(username);

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
