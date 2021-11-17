package com.sireler.kanban.controller;

import com.sireler.kanban.dto.WorkspaceDto;
import com.sireler.kanban.model.User;
import com.sireler.kanban.model.Workspace;
import com.sireler.kanban.security.jwt.JwtAuthenticationException;
import com.sireler.kanban.security.jwt.JwtUser;
import com.sireler.kanban.service.UserService;
import com.sireler.kanban.service.WorkspaceService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/v1/workspaces/")
public class WorkspaceController {

    private final WorkspaceService workspaceService;

    private final UserService userService;

    @Autowired
    public WorkspaceController(WorkspaceService workspaceService, UserService userService) {
        this.workspaceService = workspaceService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();

            User user = userService.findByUsername(username);

            List<Workspace> workspaces = user.getWorkspaces();
            List<WorkspaceDto> workspaceDtos = workspaces.stream()
                    .map(WorkspaceDto::fromWorkspace)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(workspaceDtos);
        } catch (JwtAuthenticationException e) {
            System.out.println("QWERTY");
            throw e;
        } catch (Exception e) {
            System.out.println("NQWERTY");
            throw e;
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<WorkspaceDto> getWorkspaceById(@PathVariable(name = "id") Long id) {
        Workspace workspace = workspaceService.findById(id);

        if (workspace == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        WorkspaceDto result = WorkspaceDto.fromWorkspace(workspace);

        return new ResponseEntity<WorkspaceDto>(result, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> storeWorkspace(@RequestBody WorkspaceDto workspaceDto, @AuthenticationPrincipal JwtUser jwtUser) {
        User user = userService.findByUsername(jwtUser.getUsername());
        Workspace workspace = workspaceDto.toWorkspace();
        workspaceService.create(workspace);
        user.getWorkspaces().add(workspace);
        userService.update(user);

        return ResponseEntity.ok(workspace);
    }
}
