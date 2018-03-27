package org.softuni.nuggets.services;

import org.modelmapper.ModelMapper;
import org.softuni.nuggets.entities.Employee;
import org.softuni.nuggets.models.binding.EditEmployeeBindingModel;
import org.softuni.nuggets.models.binding.RegisterBindingModel;
import org.softuni.nuggets.models.service.EmployeeServiceModel;
import org.softuni.nuggets.repositories.RoleRepository;
import org.softuni.nuggets.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {
    private final BCryptPasswordEncoder encoder;

    private final ModelMapper mapper;

    private final EmployeeRepository employeeRepository;

    private final RoleRepository roleRepository;

    @Autowired
    public EmployeeServiceImpl(BCryptPasswordEncoder encoder, ModelMapper mapper, EmployeeRepository userRepository, RoleRepository roleRepository) {
        this.encoder = encoder;
        this.mapper = mapper;
        this.employeeRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String employee) throws UsernameNotFoundException {
        Employee result = this.employeeRepository.findFirstByEgn(employee);

        if(result == null) throw new UsernameNotFoundException("Username not found.");

        return result;
    }

    @Override
    public void register(RegisterBindingModel bindingModel) {
        Employee employee = this.mapper.map(bindingModel, Employee.class);

        employee.setPassword(this.encoder.encode(bindingModel.getPassword()));

        employee.setAuthorities(new HashSet<>(this.roleRepository.findAll()));

        employee.setCredentialsNonExpired(true);
        employee.setAccountNonExpired(true);
        employee.setAccountNonLocked(true);
        employee.setEnabled(true);

        this.employeeRepository.save(employee);
    }

    @Override
    public List<Employee> getAllEmployers() {
        return this.employeeRepository.findAll();
    }

    @Override
    public EmployeeServiceModel getByEgn(String egn) {
        ModelMapper mapper = new ModelMapper();

        Employee employee = this.employeeRepository.findFirstByEgn(egn);

        EmployeeServiceModel result = mapper.map(employee, EmployeeServiceModel.class);
        result.setEgn(employee.getUsername()); // TODO fix this
        return result;
    }

    @Override
    public void editEmployer(String egn, EditEmployeeBindingModel editEmployeeBindingModel) {
        ModelMapper modelMapper = new ModelMapper();

        Employee employeeEntity = this.employeeRepository
                .findFirstByEgn(egn);

        if(employeeEntity == null) return;
        modelMapper.map(editEmployeeBindingModel,employeeEntity);
        employeeEntity.setPassword(this.encoder.encode(editEmployeeBindingModel.getPassword()));
//        employeeEntity.setEgn(editEmployeeBindingModel.getUsername());

        this.employeeRepository.save(employeeEntity);
    }

    @Override
    public void removeEmployer(String egn) {
        Employee employer = this.employeeRepository.findFirstByEgn(egn);
        if(employer != null) {
            this.employeeRepository.delete(employer);
        }
    }
}
