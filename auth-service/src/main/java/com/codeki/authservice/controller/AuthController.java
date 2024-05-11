package com.codeki.authservice.controller;

import com.codeki.authservice.dto.ReqResponse;
import com.codeki.authservice.service.AuthService;
import com.codeki.authservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    UserService userService;
    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<ReqResponse> signUp(@RequestBody ReqResponse signUpRequest) {
        return ResponseEntity.ok(authService.singUp(signUpRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<ReqResponse> logIn(@RequestBody ReqResponse logInRequest) {
        return ResponseEntity.ok(authService.login(logInRequest));
    }

    @PostMapping("/refresh")
    public ResponseEntity<ReqResponse> refreshToken(@RequestBody ReqResponse refreshTokenRequest) {
        return ResponseEntity.ok(authService.refreshToken(refreshTokenRequest));
    }

    @PostMapping("/verify")
    public ResponseEntity<ReqResponse> verifyToken(@RequestParam String verifyTokenRequest) {
        return ResponseEntity.ok(authService.verifyToken(verifyTokenRequest));
    }
}
