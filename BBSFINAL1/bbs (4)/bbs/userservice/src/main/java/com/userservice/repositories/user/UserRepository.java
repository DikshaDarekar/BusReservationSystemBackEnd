package com.userservice.repositories.user;


import com.userservice.entities.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Interface represents user repository
 */
@Repository
public interface UserRepository extends JpaRepository<User,Long> {

  Optional<User> findByEmailId(String emailId);
  Optional<User> findByMobileNumber(String mobileNumber);

}
