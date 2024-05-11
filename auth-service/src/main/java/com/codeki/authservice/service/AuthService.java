package com.codeki.authservice.service;

import com.codeki.authservice.Utils.JwtUtils;
import com.codeki.authservice.dto.ReqResponse;
import com.codeki.authservice.model.User;
import com.codeki.authservice.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class AuthService {

    @Autowired
    UserService userService;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    private UsersRepository usersRepository;

    public ReqResponse singUp(ReqResponse registrationRequest) {
        ReqResponse response = new ReqResponse();
        try {
            User user = new User();
            user.setEmail(registrationRequest.getEmail());
            user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
            user.setRole(registrationRequest.getRole());
            User userResult = usersRepository.save(user);
            if (userResult != null && userResult.getId() > 0) {
                response.setUser(userResult);
                response.setMessage("User has been registered successfully");
                response.setStatusCode(200);
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setError(e.getMessage());
        }
        return response;
    }

    public ReqResponse login(ReqResponse loginRequest) {
        ReqResponse response = new ReqResponse();
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
            User user = usersRepository.findByEmail(loginRequest.getEmail()).orElseThrow();
            System.out.println("User: " + user);
            var jwt = jwtUtils.generateToken(user);
            var refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), user);
            response.setStatusCode(200);
            response.setToken(jwt);
            response.setRefreshToken(refreshToken);
            response.setExpirationTime("24Hr");
            response.setMessage("Successfully Log in");
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setError(e.getMessage());
        }
        return response;
    }

    public ReqResponse refreshToken(ReqResponse refreshTokenRequest) {
        ReqResponse response = new ReqResponse();
        String email = jwtUtils.extractUserName(refreshTokenRequest.getToken());
        User user = usersRepository.findByEmail(email).orElseThrow();
        if (jwtUtils.validateToken(refreshTokenRequest.getToken(), user)) {
            var jwt = jwtUtils.generateToken(user);
            response.setStatusCode(200);
            response.setToken(jwt);
            response.setRefreshToken(refreshTokenRequest.getToken());
            response.setExpirationTime("24Hr");
            response.setMessage("Successfully Refresh Token");
        }
        response.setStatusCode(200);
        return response;
    }
}
