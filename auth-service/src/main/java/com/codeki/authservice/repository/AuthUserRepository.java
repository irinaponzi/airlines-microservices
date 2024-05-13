package com.codeki.authservice.repository;

import com.codeki.authservice.model.AuthUserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthUserRepository extends JpaRepository<AuthUserDetails, Long> {

    Optional<AuthUserDetails> findByUsername(String username);

}
