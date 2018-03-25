package org.softuni.nuggets.controllers;

import org.modelmapper.ModelMapper;
import org.softuni.nuggets.entities.Employee;
import org.softuni.nuggets.models.binding.EditEmployeeBindingModel;
import org.softuni.nuggets.models.binding.RegisterBindingModel;
import org.softuni.nuggets.models.service.EmployeeServiceModel;
import org.softuni.nuggets.services.EmployeeService;
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
public class AdminController {
    private EmployeeService employeeService;

    public AdminController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/register")
    public ModelAndView register(ModelAndView modelAndView, Model model) {
        modelAndView.setViewName("register");

        if (!model.containsAttribute("employerInput")) {
            model.addAttribute("employerInput", new RegisterBindingModel());
        }

        return modelAndView;
    }

    @PostMapping("/register")
    public ModelAndView registerConfirm(@ModelAttribute RegisterBindingModel bindingModel, ModelAndView modelAndView) {
        modelAndView.setViewName("redirect:/login");

        if (bindingModel.getPassword().equals(bindingModel.getConfirmPassword())) {
            this.employeeService.register(bindingModel);
        }

        return modelAndView;
    }

    @GetMapping("/all-employers")
    public ModelAndView listAllEmployees(ModelAndView modelAndView) {
        modelAndView.setViewName("all-employers");

        List<Employee> allEmployeers = this.employeeService.getAllEmployers();
        modelAndView.addObject("allEmployers", allEmployeers);
        return modelAndView;
    }

    @GetMapping("/edit/{egn}")
    public ModelAndView editEmployee(@PathVariable("egn") String egn, ModelAndView modelAndView,Model model,ModelMapper modelMapper) {
        modelAndView.setViewName("edit-employer");
        EmployeeServiceModel employeeByEgn = this.employeeService.getByEgn(egn);

        if (!model.containsAttribute("employerInput")) {
            EditEmployeeBindingModel bindingModel = modelMapper.map(employeeByEgn, EditEmployeeBindingModel.class);

            model.addAttribute("employerInput", bindingModel);
        }

        return modelAndView;
    }

    @PostMapping("/edit/{egn}")
    public ModelAndView editEmployerForm(@PathVariable String egn, @ModelAttribute(name = "employerInput") EditEmployeeBindingModel editEmployeeBindingModel,
                                         BindingResult bindingResult, ModelAndView modelAndView, RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.employerInput", bindingResult);
            redirectAttributes.addFlashAttribute("employerInput", editEmployeeBindingModel);

            modelAndView.setViewName("redirect:home");
        } else {
            this.employeeService.editEmployer(egn, editEmployeeBindingModel);
            modelAndView.setViewName("redirect:/all-employers");
        }

        return modelAndView;
    }

    @GetMapping("/delete/{egn}")
    public ModelAndView deleteVirus(@PathVariable String egn, ModelAndView modelAndView, Model model, ModelMapper modelMapper) {
        EmployeeServiceModel employeeByEgn = this.employeeService.getByEgn(egn);

        modelAndView.setViewName("remove-employer");

        model.addAttribute("employerInput", modelMapper.map(employeeByEgn, EditEmployeeBindingModel.class)); //TODO fix with deleteBinding?


        return modelAndView;
    }

    @PostMapping("/delete/{egn}")
    public ModelAndView removeConfirm(@PathVariable String egn, ModelAndView modelAndView) {
        this.employeeService.removeEmployer(egn);

        modelAndView.setViewName("redirect:/all-employers");

        return modelAndView;
    }
}
