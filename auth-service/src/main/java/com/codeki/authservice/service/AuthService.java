package com.codeki.authservice.service;

import com.codeki.authservice.Utils.JwtUtils;
import com.codeki.authservice.dto.ReqResponse;
import com.codeki.authservice.model.CustomUserDetails;
import com.codeki.authservice.model.User;
import com.codeki.authservice.repository.CustomUserDetailsRepository;
import com.codeki.authservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    CustomUserDetailsRepository customUserDetailsRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    AuthenticationManager authenticationManager;

    public ReqResponse singUp(ReqResponse registrationReq) {
        Optional<CustomUserDetails> authUserOptional = customUserDetailsRepository.findByUsername(registrationReq.getUsername());
        if(authUserOptional.isEmpty()) {
            CustomUserDetails authUser = new CustomUserDetails();

            authUser.setUsername(registrationReq.getUsername());
            authUser.setEmail(registrationReq.getEmail());
            authUser.setPassword(passwordEncoder.encode(registrationReq.getPassword()));
            authUser.setRole(registrationReq.getRole());

            authUser.setUser(createUser(registrationReq));
            customUserDetailsRepository.save(authUser);

            ReqResponse response = new ReqResponse();
            response.setMessage("User has been registered successfully");
            response.setStatusCode(200);
            return response;

        } throw new DuplicateKeyException("Username already exists");
    }

    public ReqResponse logIn(ReqResponse loginReq) {
        ReqResponse response = new ReqResponse();
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginReq.getUsername(), loginReq.getPassword()));
            Optional<CustomUserDetails> authUser = customUserDetailsRepository.findByUsername(loginReq.getUsername());

            if (authUser.isPresent()) {
                String TokenJwt = jwtUtils.generateToken(authUser.get());
                response.setToken(TokenJwt);
                response.setStatusCode(200);
                response.setExpirationTime("1 Hr");
                response.setMessage("Successfully Log in");
                return response;
            }
        } catch (AuthenticationException e) {
            response.setMessage("Unauthorized: Invalid username or password");
            response.setStatusCode(401);
        }
        return response;
    }

    public ReqResponse validateToken(String token) {
        ReqResponse response = new ReqResponse();
        try {
            String userName = jwtUtils.extractUserName(token);
            CustomUserDetails authUser = customUserDetailsRepository.findByUsername(userName)
                    .orElseThrow(() -> new UsernameNotFoundException("Unauthorized: Token is not valid"));

            if (jwtUtils.validateToken(token, authUser)) {
                response.setStatusCode(200);
                response.setMessage("Token is valid");
            } else {
                response.setStatusCode(401);
                response.setMessage("Unauthorized: Token is not valid");
            }
        } catch (Exception e) {
            response.setStatusCode(401);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    private User createUser(ReqResponse registrationReq) {
        User user = new User();
        user.setName(registrationReq.getName());
        user.setLastName(registrationReq.getLastName());
        user.setPassport(registrationReq.getPassport());
        user.setDni(registrationReq.getDni());

        return userRepository.save(user);
    }

    // --- LO QUE FALTA ---
    // Log out register
    // Update registrer
    // Delete register
    // User el delete lo saco?


}
