package org.softuni.nuggets.services;

import org.softuni.nuggets.entities.Employee;
import org.softuni.nuggets.models.binding.EditEmployeeBindingModel;
import org.softuni.nuggets.models.binding.RegisterBindingModel;
import org.softuni.nuggets.models.service.EmployeeServiceModel;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface EmployeeService extends UserDetailsService {
    void register(RegisterBindingModel bindingModel);

    List<Employee> getAllEmployers();

    EmployeeServiceModel getByEgn(String egn);

    void editEmployer(String egn, EditEmployeeBindingModel editEmployeeBindingModel);

    void removeEmployer(String id);
}
