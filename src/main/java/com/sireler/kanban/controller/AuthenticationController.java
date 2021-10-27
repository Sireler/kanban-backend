package com.sireler.kanban.controller;

import com.sireler.kanban.dto.AuthenticationRequestDto;
import com.sireler.kanban.dto.UserDto;
import com.sireler.kanban.model.User;
import com.sireler.kanban.security.jwt.JwtTokenProvider;
import com.sireler.kanban.service.UserService;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/v1/auth/")
public class AuthenticationController {

    private AuthenticationManager authenticationManager;

    private JwtTokenProvider jwtTokenProvider;

    private UserService userService;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @PostMapping("login")
    public ResponseEntity login(@RequestBody AuthenticationRequestDto requestDto) {
        try {
            String username = requestDto.getUsername();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, requestDto.getPassword()));
            User user = userService.findByUsername(username);

            if (user == null) {
                throw new UsernameNotFoundException("User with username: " + username + " not found");
            }

            String token = jwtTokenProvider.createToken(username, user.getRoles());
            Map<Object, Object> response = new HashMap<>();
            response.put("username", username);
            response.put("token", token);

            return ResponseEntity.ok(response);

        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    @PostMapping("register")
    public ResponseEntity<User> register(@RequestBody UserDto userDto, HttpServletRequest request) {
        try {
            User user = userDto.toUser();
            User registeredUser = userService.register(user);

            return ResponseEntity.ok(registeredUser);
        } catch (ConstraintViolationException e) {
            throw new BadCredentialsException(e.getMessage());
        }
    }

    @PostMapping("me")
    public ResponseEntity<User> me(@RequestHeader("Authorization") String authorization) {
        String token = jwtTokenProvider.getTokenFromAuthorization(authorization);
        String username = jwtTokenProvider.getUsername(token);

        User user = userService.findByUsername(username);

        return ResponseEntity.ok(user);
    }
}
