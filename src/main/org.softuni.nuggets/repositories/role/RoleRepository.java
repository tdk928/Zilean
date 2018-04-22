package org.softuni.nuggets.repositories.role;

import org.softuni.nuggets.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
    Role findById(long id);

    Role findByAuthority(String authority);
}
