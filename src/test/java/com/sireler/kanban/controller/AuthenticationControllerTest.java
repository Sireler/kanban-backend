package com.sireler.kanban.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.sireler.kanban.dto.AuthenticationRequestDto;
import com.sireler.kanban.dto.UserDto;
import com.sireler.kanban.model.User;
import com.sireler.kanban.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class AuthenticationControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Test
    void ShouldLogin() throws Exception {
        String username = "testLoginUsername";
        String password = "secret";

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setEmail("testLoginEmail@gmail.com");
        user.setFirstName("ivan");
        user.setLastName("ivanov");

        userRepository.save(user);

        AuthenticationRequestDto requestDto = new AuthenticationRequestDto();
        requestDto.setUsername(username);
        requestDto.setPassword(password);

        this.mockMvc
                .perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(requestDto))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is(username)))
                .andExpect(jsonPath("$", hasKey("token")));
    }

    @Test
    void ShouldRegisterWithValidRequest() throws Exception {
        String username = "testUsername";

        UserDto userDto = new UserDto();
        userDto.setUsername(username);
        userDto.setPassword("secret");
        userDto.setEmail("email@gmail.com");
        userDto.setFirstName("ivan");
        userDto.setLastName("ivanov");

        this.mockMvc
                .perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(userDto))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is(username)));

        User registeredUser = userRepository.findByUsername(username);
        Assertions.assertEquals(username, registeredUser.getUsername());
    }

    @Test
    void ShouldSendUserData() throws Exception {
        String username = "testMe";
        String password = "secret";

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setEmail("testMe@gmail.com");
        user.setFirstName("ivan");
        user.setLastName("ivanov");

        userRepository.save(user);

        AuthenticationRequestDto requestDto = new AuthenticationRequestDto();
        requestDto.setUsername(username);
        requestDto.setPassword(password);

        MvcResult result = this.mockMvc
                .perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(requestDto))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is(username)))
                .andExpect(jsonPath("$", hasKey("token")))
                .andReturn();

        String token = JsonPath.read(result.getResponse().getContentAsString(), "$.token");
        this.mockMvc
                .perform(post("/api/v1/auth/me")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .header("Authorization", "Bearer " + token)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(user.getId().intValue())))
                .andExpect(jsonPath("$.username", is(username)))
                .andExpect(jsonPath("$.email", is(user.getEmail())))
                .andExpect(jsonPath("$.first_name", is(user.getFirstName())))
                .andExpect(jsonPath("$.last_name", is(user.getLastName())));
    }

    @Test
    void ShouldSendErrorWhenUsernameAlreadyTakenOnRegister() throws Exception {
        String username = "testUsernameTaken";
        String password = "secret";

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setEmail("errorUsername1@gmail.com");
        user.setFirstName("ivan");
        user.setLastName("ivanov");

        userRepository.save(user);

        UserDto userDto = new UserDto();
        userDto.setUsername(username);
        userDto.setPassword("secret");
        userDto.setEmail("errorUsername2@gmail.com");
        userDto.setFirstName("ivan");
        userDto.setLastName("ivanov");

        this.mockMvc
                .perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(userDto))
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", is("Username is already taken")));
    }

    @Test
    void ShouldSendErrorWhenEmailAlreadyTakenOnRegister() throws Exception {
        String password = "secret";
        String email = "sameEmail@gmail.com";

        User user = new User();
        user.setUsername("usernameNotTaken");
        user.setPassword(passwordEncoder.encode(password));
        user.setEmail(email);
        user.setFirstName("ivan");
        user.setLastName("ivanov");

        userRepository.save(user);

        UserDto userDto = new UserDto();
        userDto.setUsername("newUsername");
        userDto.setPassword("secret");
        userDto.setEmail(email);
        userDto.setFirstName("ivan");
        userDto.setLastName("ivanov");

        this.mockMvc
                .perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(userDto))
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", is("Email is already taken")));
    }
}