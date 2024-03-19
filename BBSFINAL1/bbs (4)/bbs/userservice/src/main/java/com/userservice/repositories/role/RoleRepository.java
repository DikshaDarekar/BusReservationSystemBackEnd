package com.userservice.repositories.role;

import com.userservice.entities.role.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Interface represents role repository
 */
@Repository
public interface RoleRepository extends JpaRepository<Role,Short> {

  Optional<Role> findByName(String name);

}
