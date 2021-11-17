package com.sireler.kanban.controller;

import com.sireler.kanban.dto.AuthenticationRequestDto;
import com.sireler.kanban.dto.JwtDto;
import com.sireler.kanban.dto.UserDto;
import com.sireler.kanban.exception.KanbanApiException;
import com.sireler.kanban.model.User;
import com.sireler.kanban.security.jwt.JwtTokenProvider;
import com.sireler.kanban.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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
    public ResponseEntity<JwtDto> login(@RequestBody AuthenticationRequestDto requestDto) {
        try {
            String username = requestDto.getUsername();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, requestDto.getPassword()));
            User user = userService.findByUsername(username);

            if (user == null) {
                throw new KanbanApiException(HttpStatus.BAD_REQUEST, "User with username: " + username + " not found");
            }

            String token = jwtTokenProvider.createToken(username, user.getRoles());
            JwtDto jwtDto = new JwtDto(username, token);

            return ResponseEntity.ok(jwtDto);
        } catch (AuthenticationException e) {
            throw new KanbanApiException(HttpStatus.BAD_REQUEST, "Invalid username or password");
        }
    }

    @PostMapping("register")
    public ResponseEntity<UserDto> register(@Valid @RequestBody UserDto userDto, HttpServletRequest request) {
        try {
            //TODO: check username and email available
            User user = userDto.toUser();
            User registeredUser = userService.register(user);
            UserDto userResponse = UserDto.fromUser(registeredUser);

            return ResponseEntity.ok(userResponse);
        } catch (Exception e) {
            //TODO: change message to more informative
            throw new KanbanApiException(HttpStatus.BAD_REQUEST, "Register error");
        }
    }

    @PostMapping("me")
    public ResponseEntity<UserDto> me(@RequestHeader("Authorization") String authorization) {
        String token = jwtTokenProvider.getTokenFromAuthorization(authorization);
        String username = jwtTokenProvider.getUsername(token);

        User user = userService.findByUsername(username);
        UserDto userDto = UserDto.fromUser(user);

        return ResponseEntity.ok(userDto);
    }
}
