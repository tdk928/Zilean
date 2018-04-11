package org.softuni.nuggets.areas.user.services;

import org.modelmapper.ModelMapper;
import org.softuni.nuggets.entities.Employee;
import org.softuni.nuggets.models.service.EmployeeServiceModel;
import org.softuni.nuggets.areas.user.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {


        this.employeeRepository = employeeRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String employee) throws UsernameNotFoundException {
        Employee result = this.employeeRepository.findFirstByUsername(employee);

        if(result == null) throw new UsernameNotFoundException("Username not found.");

        return result;
    }


    @Override
    public EmployeeServiceModel getByUsername(String egn) {
        ModelMapper mapper = new ModelMapper();

        Employee employee = this.employeeRepository.findFirstByUsername(egn);

        EmployeeServiceModel result = mapper.map(employee, EmployeeServiceModel.class);
        result.setUsername(employee.getUsername()); // TODO fix this
        return result;
    }

}
