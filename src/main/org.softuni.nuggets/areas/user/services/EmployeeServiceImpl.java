package org.softuni.nuggets.areas.user.services;

import org.modelmapper.ModelMapper;
import org.softuni.nuggets.entities.Employee;
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
    private final ModelMapper modelMapper;
    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, BCryptPasswordEncoder encoder, ModelMapper modelMapper) {


        this.employeeRepository = employeeRepository;
        this.encoder = encoder;
        this.modelMapper = modelMapper;
    }

    private void configureUserDetailsBug(Employee employee) {
        employee.setAccountNonExpired(true);
        employee.setAccountNonLocked(true);
        employee.setCredentialsNonExpired(true);
        employee.setEnabled(true);

    }

    @Override
    public UserDetails loadUserByUsername(String employee) throws UsernameNotFoundException {
        Employee result = this.employeeRepository.findFirstByUsernameAndDeletedOnIsNull(employee);

        if(result == null) throw new UsernameNotFoundException("Username not found.");

        return result;
    }


    @Override
    public EmployeeServiceModel getByUsernameAndDeletedOnIsNotNull(String egn) {
        ModelMapper mapper = new ModelMapper();

        Employee employee = this.employeeRepository.findFirstByUsernameAndDeletedOnIsNull(egn);

        EmployeeServiceModel result = mapper.map(employee, EmployeeServiceModel.class);
        result.setUsername(employee.getUsername()); // TODO fix this
        return result;
    }

    @Override
    public void editEmployer(String username, UserEditEmployeeBindingModel model) {
        ModelMapper modelMapper = new ModelMapper();

        Employee employeeEntity = this.employeeRepository
                .findFirstByUsernameAndDeletedOnIsNull(username);

        if(employeeEntity == null) return;
        if (!model.getPassword().trim().equals(employeeEntity.getPassword())) {
            employeeEntity.setPassword(this.encoder.encode(model.getPassword()));
        }
//        modelMapper.map(model,employeeEntity);

//        this.configureUserDetailsBug(employeeEntity);
        this.employeeRepository.save(employeeEntity);
    }

    @Override
    public void save(EmployeeServiceModel model) {
        Employee employee = this.modelMapper.map(model, Employee.class);
        this.employeeRepository.save(employee);
    }
}
