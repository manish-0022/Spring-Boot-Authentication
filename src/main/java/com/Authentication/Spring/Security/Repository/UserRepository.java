package com.Authentication.Spring.Security.Repository;

import com.Authentication.Spring.Security.Entities.Role;
import com.Authentication.Spring.Security.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {


    Optional<User> findByEmail(String email);

    User findByRole(Role role);
}
