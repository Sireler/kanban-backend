package com.sireler.kanban.controller;

import com.sireler.kanban.dto.WorkspaceDto;
import com.sireler.kanban.model.User;
import com.sireler.kanban.model.Workspace;
import com.sireler.kanban.security.jwt.JwtUser;
import com.sireler.kanban.service.UserService;
import com.sireler.kanban.service.WorkspaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public ResponseEntity<List<WorkspaceDto>> getAll() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userService.findByUsername(username);

        List<Workspace> workspaces = user.getWorkspaces();
        List<WorkspaceDto> result = workspaces.stream()
                .map(WorkspaceDto::fromWorkspace)
                .collect(Collectors.toList());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<WorkspaceDto> getWorkspaceById(@PathVariable(name = "id") Long id) {
        //TODO: check user permissions
        Workspace workspace = workspaceService.findById(id);

        if (workspace == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        WorkspaceDto result = WorkspaceDto.fromWorkspace(workspace);

        return new ResponseEntity<WorkspaceDto>(result, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<WorkspaceDto> storeWorkspace(@Valid @RequestBody WorkspaceDto workspaceDto, @AuthenticationPrincipal JwtUser jwtUser) {
        User user = userService.findByUsername(jwtUser.getUsername());
        Workspace workspace = workspaceDto.toWorkspace();
        workspaceService.create(workspace);
        user.getWorkspaces().add(workspace);
        userService.update(user);

        WorkspaceDto result = WorkspaceDto.fromWorkspace(workspace);

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }
}
