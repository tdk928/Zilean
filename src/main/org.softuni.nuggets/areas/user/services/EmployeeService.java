package org.softuni.nuggets.areas.user.services;

import org.softuni.nuggets.models.binding.UserEditEmployeeBindingModel;
import org.softuni.nuggets.models.service.EmployeeServiceModel;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface EmployeeService extends UserDetailsService {
    EmployeeServiceModel getByUsernameAndDeletedOnIsNotNull(String username);

    void editEmployer(String username, UserEditEmployeeBindingModel model);

    void save(EmployeeServiceModel employee);
}
