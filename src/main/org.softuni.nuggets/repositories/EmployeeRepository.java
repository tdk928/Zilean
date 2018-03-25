package org.softuni.nuggets.repositories;

import org.softuni.nuggets.entities.Employee;
import org.softuni.nuggets.models.service.EmployeeServiceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {
    Employee findFirstByEgn(String employee);

//    EmployeeServiceModel findByEgn(String egn);
}
