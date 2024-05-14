package com.codeki.authservice.controller;

import com.codeki.authservice.dto.ReqResponse;
import com.codeki.authservice.model.User;
import com.codeki.authservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("")
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return new ResponseEntity<>(userService.findById(id), HttpStatus.OK);
    }

    @GetMapping("/passport")
    public ResponseEntity<User> getUserByPassport(@RequestParam String passport) {
        return new ResponseEntity<>(userService.findByPassport(passport), HttpStatus.OK);
    }

    @GetMapping("/lastname")
    public ResponseEntity<List<User>> getUserByLastName(@RequestParam String lastname) {
        return new ResponseEntity<>(userService.findByLastName(lastname), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        return new ResponseEntity<>(userService.update(id, user), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ReqResponse> deleteCompany(@PathVariable Long id) {
        return new ResponseEntity<>(userService.deleteById(id), HttpStatus.OK);
    }

}
