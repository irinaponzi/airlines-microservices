package com.codeki.authservice.repository;

import com.codeki.authservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByPassport(String passport);
    List<User> findUserByLastNameContainingIgnoreCase(String lastName);

}
