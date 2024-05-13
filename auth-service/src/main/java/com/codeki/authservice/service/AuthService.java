package com.codeki.authservice.service;

import com.codeki.authservice.Utils.JwtUtils;
import com.codeki.authservice.dto.ReqResponse;
import com.codeki.authservice.model.AuthUserDetails;
import com.codeki.authservice.repository.AuthUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    AuthUserRepository authUserRepository;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    AuthenticationManager authenticationManager;


    public ReqResponse singUp(ReqResponse registrationReq) {
        ReqResponse response = new ReqResponse();

        try {
            AuthUserDetails authUser = new AuthUserDetails();
            authUser.setUsername(registrationReq.getUsername());
            authUser.setEmail(registrationReq.getEmail());
            authUser.setPassword(passwordEncoder.encode(registrationReq.getPassword()));
            authUser.setRole(registrationReq.getRole());

            authUserRepository.save(authUser);

            response.setMessage("User has been registered successfully");
            response.setStatusCode(200);

        // trabajar esta excepcion
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setError(e.getMessage());
        }
        return response;
    }  // hacer que chequee que el nombre de usuario es único

    public ReqResponse login(ReqResponse loginReq) {
        ReqResponse response = new ReqResponse();

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginReq.getUsername(), loginReq.getPassword()));
            AuthUserDetails authUser = authUserRepository.findByUsername(loginReq.getUsername())
                    .orElseThrow(() -> new UsernameNotFoundException("Username not found")); // ver esta excepcion

            String TokenJwt = jwtUtils.generateToken(authUser);

            response.setToken(TokenJwt);
            response.setStatusCode(200);
            response.setExpirationTime("1 Hr");
            response.setMessage("Successfully Log in");

        // trabajar esta excepcion
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setError(e.getMessage());
        }
        return response;
    }

    public ReqResponse validateToken(String token) {
        ReqResponse response = new ReqResponse();

        try {
            String userName = jwtUtils.extractUserName(token);
            AuthUserDetails authUser = authUserRepository.findByUsername(userName)
                    .orElseThrow(() -> new UsernameNotFoundException("Username not found")); // ver esta excepcion

            if (jwtUtils.validateToken(token, authUser)) {
                response.setStatusCode(200);
                response.setMessage("Token is valid");
            } else {
                response.setStatusCode(401); // Unauthorized
                response.setMessage("Token is not valid");
            }

        // trabajar esta excepcion
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setError(e.getMessage());
        }
        return response;
    }

    // Falta Log out.
}
