package com.codeki.authservice.service;

import com.codeki.authservice.dto.ReqResponse;
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
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            return userOptional.get();
        }
        throw new NotFoundException("User not found");
    }

    public User findByPassport(String passport) {
        Optional<User> userOptional = userRepository.findUserByPassport(passport);
        if (userOptional.isPresent()) {
            return userOptional.get();
        }
        throw new NotFoundException("User not found");
    }

    public List<User> findByLastName(String lastName) {
        List<User> usersList = userRepository.findUserByLastNameContainingIgnoreCase(lastName);
        if (!usersList.isEmpty()) {
            return usersList;
        }
        throw new NotFoundException("No results found");
    }

    public User update(Long id, User user) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            user.setId(id);
            return userRepository.save(user);
        }
        throw new NotFoundException("Unable to update: User not found");
    }

    public ReqResponse deleteById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            userRepository.deleteById(id);
            ReqResponse reqResponse = new ReqResponse();
            reqResponse.setMessage("The user " + id + " has been deleted");
            return reqResponse;
        }
        throw new NotFoundException("Unable to delete: User not found");
    }
}
