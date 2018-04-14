package org.softuni.nuggets.areas.user.services;

import org.modelmapper.ModelMapper;
import org.softuni.nuggets.entities.Employee;
import org.softuni.nuggets.models.binding.AdminEditEmployeeBindingModel;
import org.softuni.nuggets.models.binding.UserEditEmployeeBindingModel;
import org.softuni.nuggets.models.service.EmployeeServiceModel;
import org.softuni.nuggets.areas.user.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final BCryptPasswordEncoder encoder;
    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, BCryptPasswordEncoder encoder) {


        this.employeeRepository = employeeRepository;
        this.encoder = encoder;
    }

    private void configureUserDetailsBug(Employee employee) {
        employee.setAccountNonExpired(true);
        employee.setAccountNonLocked(true);
        employee.setCredentialsNonExpired(true);
        employee.setEnabled(true);

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

    @Override
    public void editEmployer(String username, UserEditEmployeeBindingModel model) {
        ModelMapper modelMapper = new ModelMapper();

        Employee employeeEntity = this.employeeRepository
                .findFirstByUsername(username);

        if(employeeEntity == null) return;
        if (!model.getPassword().trim().equals(employeeEntity.getPassword())) {
            model.setPassword(this.encoder.encode(model.getPassword()));
        }
        modelMapper.map(model,employeeEntity);

        this.configureUserDetailsBug(employeeEntity);
        this.employeeRepository.save(employeeEntity);
    }
}
