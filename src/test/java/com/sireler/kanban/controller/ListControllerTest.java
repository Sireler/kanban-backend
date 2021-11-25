package com.sireler.kanban.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sireler.kanban.dto.ListDto;
import com.sireler.kanban.model.List;
import com.sireler.kanban.model.User;
import com.sireler.kanban.model.Workspace;
import com.sireler.kanban.repository.ListRepository;
import com.sireler.kanban.repository.UserRepository;
import com.sireler.kanban.repository.WorkspaceRepository;
import com.sireler.kanban.security.jwt.JwtUser;
import org.junit.jupiter.api.Assertions;
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
public class ListControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    ListRepository listRepository;

    @Autowired
    WorkspaceRepository workspaceRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    public void shouldRejectWithoutAuthorization() throws Exception {
        Workspace workspace = new Workspace();
        workspace.setName("Workspace for lists");

        this.workspaceRepository.save(workspace);

        this.mockMvc
                .perform(get("/api/v1/workspaces/" + workspace.getId() + "/lists"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(value = "user", userDetailsServiceBeanName = "jwtUserDetailsService")
    public void shouldShowListsWhenJwtTokenValid() throws Exception {
        JwtUser jwtUser = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = this.userRepository.findByUsername(jwtUser.getUsername());

        Workspace workspace = new Workspace();
        workspace.setName("Workspace for lists");
        workspace.setUser(user);
        this.workspaceRepository.save(workspace);

        List list = new List();
        list.setTitle("List 1");
        list.setWorkspace(workspace);
        this.listRepository.save(list);

        this.mockMvc
                .perform(get("/api/v1/workspaces/" + workspace.getId() + "/lists"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(list.getId().intValue())))
                .andExpect(jsonPath("$[0].title", is("List 1")));
    }

    @Test
    @WithUserDetails(value = "user", userDetailsServiceBeanName = "jwtUserDetailsService")
    public void shouldShowListById() throws Exception {
        JwtUser jwtUser = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = this.userRepository.findByUsername(jwtUser.getUsername());

        Workspace workspace = new Workspace();
        workspace.setName("Workspace for lists");
        workspace.setUser(user);
        this.workspaceRepository.save(workspace);

        List list = new List();
        list.setTitle("List by id");
        list.setWorkspace(workspace);
        this.listRepository.save(list);

        this.mockMvc
                .perform(get("/api/v1/workspaces/" + workspace.getId() + "/lists/" + list.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(list.getId().intValue())))
                .andExpect(jsonPath("$.title", is("List by id")));
    }

    @Test
    @WithUserDetails(value = "user", userDetailsServiceBeanName = "jwtUserDetailsService")
    public void shouldStoreList() throws Exception {
        JwtUser jwtUser = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = this.userRepository.findByUsername(jwtUser.getUsername());

        Workspace workspace = new Workspace();
        workspace.setName("Workspace for lists");
        workspace.setUser(user);
        this.workspaceRepository.save(workspace);

        ListDto listDto = new ListDto();
        listDto.setTitle("Store list");

        this.mockMvc
                .perform(
                        post("/api/v1/workspaces/" + workspace.getId() + "/lists")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .accept(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(listDto))
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title", is("Store list")));
    }

    @Test
    @WithUserDetails(value = "user", userDetailsServiceBeanName = "jwtUserDetailsService")
    public void shouldDeleteList() throws Exception {
        JwtUser jwtUser = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = this.userRepository.findByUsername(jwtUser.getUsername());

        Workspace workspace = new Workspace();
        workspace.setName("Workspace for lists");
        workspace.setUser(user);
        this.workspaceRepository.save(workspace);

        List list = new List();
        list.setTitle("List by id");
        list.setWorkspace(workspace);
        List savedList = this.listRepository.save(list);

        this.mockMvc
                .perform(delete("/api/v1/workspaces/" + workspace.getId() + "/lists/" + savedList.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("List deleted successfully")));

        List deletedList = listRepository.findById(list.getId()).orElse(null);
        Assertions.assertNull(deletedList);
    }
}
