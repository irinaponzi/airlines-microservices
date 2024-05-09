package com.codeki.authservice.service;

import com.codeki.authservice.dto.TokenDto;
import com.codeki.authservice.dto.UserDto;
import com.codeki.authservice.model.User;
import com.codeki.authservice.repository.UserRepository;
import com.codeki.authservice.security.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtProvider jwtProvider;

    public User save(UserDto userDto) {
        Optional<User> userOptional = userRepository.findByUsername(userDto.getUsername());
        if (userOptional.isPresent()) {
            return null;
        }

        String encodedPassword = passwordEncoder.encode(userDto.getPassword());
        User user = User.builder()
                .username(userDto.getUsername())
                .password(encodedPassword)
                .build();

        return userRepository.save(user);
    }

    public TokenDto login(UserDto userDto) {
        Optional<User> userOptional = userRepository.findByUsername(userDto.getUsername());
        if (userOptional.isEmpty()) {
            return null;
        }
        if (!passwordEncoder.matches(userDto.getPassword(), userOptional.get().getPassword())) {
            return new TokenDto(jwtProvider.createToken(userOptional.get()));
        }
        return null;
    }

    public TokenDto validateToken(String token) {
        if (!jwtProvider.validateToken(token)) {
            return null;
        }
        String username = jwtProvider.getUsernameFromToken(token);
        if (userRepository.findByUsername(username).isEmpty()) {
            return null;
        }
        return new TokenDto(token);
    }

}
