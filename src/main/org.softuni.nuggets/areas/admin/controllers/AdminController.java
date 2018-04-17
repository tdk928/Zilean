package org.softuni.nuggets.areas.admin.controllers;

import org.modelmapper.ModelMapper;
import org.softuni.nuggets.areas.admin.services.AdminService;
import org.softuni.nuggets.controllers.BaseController;
import org.softuni.nuggets.models.binding.AdminEditEmployeeBindingModel;
import org.softuni.nuggets.models.binding.RegisterBindingModel;
import org.softuni.nuggets.models.service.EmployeeServiceModel;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

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

        return this.view("admin/register");
    }

    @PostMapping("/register")
    public ModelAndView registerConfirm(@Valid @ModelAttribute RegisterBindingModel bindingModel, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return this.redirect("register");
        } else {
            if (bindingModel.getPassword().equals(bindingModel.getConfirmPassword())) {
                this.adminService.register(bindingModel);
            }
        }

        return this.redirect("login");
    }


    @GetMapping("/admin/all-employers")
    public ModelAndView listAllEmployees() {
        return this.view("/admin/all-employers", "allEmployers", this.adminService.getAllEmployers());

    }

    @GetMapping("/admin/edit/{username}")
    public ModelAndView editEmployee(@PathVariable("username") String username, Model model, ModelMapper modelMapper) {
        EmployeeServiceModel employeeByUsername = this.adminService.getByUsername(username);

        if (!model.containsAttribute("employerInput")) {
            AdminEditEmployeeBindingModel bindingModel = modelMapper.map(employeeByUsername, AdminEditEmployeeBindingModel.class);

            model.addAttribute("employerInput", bindingModel);
        }

        return this.view("/admin/edit-employer");
    }

    @PostMapping("/admin/edit/{username}")
    public ModelAndView editEmployerForm(@PathVariable String username, @ModelAttribute(name = "employerInput") AdminEditEmployeeBindingModel editEmployeeBindingModel,
                                         BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.employerInput", bindingResult);
            redirectAttributes.addFlashAttribute("employerInput", editEmployeeBindingModel);

            return this.redirect("home");
        } else {
            this.adminService.editEmployer(username, editEmployeeBindingModel);
//            this.userCache.removeUserFromCache(egn);
            return this.redirect("admin/all-employers");
        }


    }

    @GetMapping("/admin/delete/{username}")
    public ModelAndView deleteEmployer(@PathVariable String username, Model model, ModelMapper modelMapper) {
        EmployeeServiceModel employeeByUsername = this.adminService.getByUsername(username);

        model.addAttribute("employerInput", modelMapper.map(employeeByUsername, AdminEditEmployeeBindingModel.class)); //TODO fix with deleteBinding?

        return this.view("/admin/remove-employer");
    }

    @PostMapping("/admin/delete/{username}")
    public ModelAndView removeConfirm(@PathVariable String username) {
        this.adminService.removeEmployer(username);
        return this.redirect("admin/all-employers");
    }

}
