package com.sireler.kanban.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sireler.kanban.dto.WorkspaceDto;
import com.sireler.kanban.model.User;
import com.sireler.kanban.model.Workspace;
import com.sireler.kanban.repository.UserRepository;
import com.sireler.kanban.repository.WorkspaceRepository;
import com.sireler.kanban.security.jwt.JwtUser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class WorkspaceControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    WorkspaceRepository workspaceRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    public void shouldRejectWhenJwtTokenInvalid() throws Exception {
        this.mockMvc
                .perform(get("/api/v1/workspaces/"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(value = "user", userDetailsServiceBeanName = "jwtUserDetailsService")
    public void shouldShowWorkspacesWhenJwtTokenValid() throws Exception {
        JwtUser jwtUser = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = this.userRepository.findByUsername(jwtUser.getUsername());
        Workspace workspace = new Workspace();
        workspace.setName("first test workspace");
        workspace.setUser(user);
        this.workspaceRepository.save(workspace);

        this.mockMvc
                .perform(get("/api/v1/workspaces/"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("first test workspace")));
    }

    @Test
    @WithUserDetails(value = "user", userDetailsServiceBeanName = "jwtUserDetailsService")
    public void shouldStoreWorkspace() throws Exception {
        WorkspaceDto workspaceDto = new WorkspaceDto();
        workspaceDto.setName("test workspace");

        this.mockMvc
                .perform(
                        post("/api/v1/workspaces/")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .accept(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(workspaceDto))
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("test workspace")));
    }

    @Test
    @WithUserDetails(value = "user", userDetailsServiceBeanName = "jwtUserDetailsService")
    public void shouldDeleteWorkspace() throws Exception {
        JwtUser jwtUser = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = this.userRepository.findByUsername(jwtUser.getUsername());
        Workspace workspace = new Workspace();
        workspace.setName("delete workspace");
        workspace.setUser(user);
        Workspace savedWorkspace = this.workspaceRepository.save(workspace);

        this.mockMvc
                .perform(delete("/api/v1/workspaces/" + savedWorkspace.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Workspace deleted successfully")));
    }
}
