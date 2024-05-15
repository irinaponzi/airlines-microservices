package com.codeki.authservice.controller;

import com.codeki.authservice.dto.ReqResponse;
import com.codeki.authservice.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/sign-up")
    public ResponseEntity<ReqResponse> signUp(@RequestBody ReqResponse signUpReq) {
        return new ResponseEntity<>(authService.singUp(signUpReq), HttpStatus.CREATED);
    }

    @PostMapping("/log-in")
    public ResponseEntity<ReqResponse> logIn(@RequestBody ReqResponse logInReq) {
        return new ResponseEntity<>(authService.logIn(logInReq), HttpStatus.OK);
    }

    @PostMapping("/validate-token")
    public ResponseEntity<ReqResponse> validateToken(@RequestParam String validateTokenReq) {
        return new ResponseEntity<>(authService.validateToken(validateTokenReq), HttpStatus.OK);
    }

    @PutMapping("/update-account")
    public ResponseEntity<ReqResponse> updateAccount(@RequestParam String username, @RequestBody ReqResponse updateReq) {
        return new ResponseEntity<>(authService.updateAccount(username, updateReq), HttpStatus.OK);
    }

    @DeleteMapping("/delete-account/{username}")
    public ResponseEntity<ReqResponse> deleteAccount(@PathVariable String username) {
        return new ResponseEntity<>(authService.deleteAccount(username), HttpStatus.OK);
    }
}
