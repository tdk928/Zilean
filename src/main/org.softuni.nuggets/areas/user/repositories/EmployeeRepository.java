package org.softuni.nuggets.areas.user.repositories;

import org.softuni.nuggets.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {
    Employee findFirstByUsername(String employee);
}
