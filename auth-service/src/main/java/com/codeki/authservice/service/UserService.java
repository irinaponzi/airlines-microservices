package com.codeki.authservice.service;

import com.codeki.authservice.exceptions.NotFoundException;
import com.codeki.authservice.model.User;
import com.codeki.authservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        Optional<User> companyOptional = userRepository.findById(id);
        if (companyOptional.isPresent()) {
            return companyOptional.get();
        }
        throw new NotFoundException("User not found");
    }
}
