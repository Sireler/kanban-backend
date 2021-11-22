package com.sireler.kanban.controller;

import com.sireler.kanban.dto.ResponseDto;
import com.sireler.kanban.dto.WorkspaceDto;
import com.sireler.kanban.model.Workspace;
import com.sireler.kanban.security.jwt.JwtUser;
import com.sireler.kanban.service.WorkspaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/v1/workspaces/")
public class WorkspaceController {

    private final WorkspaceService workspaceService;

    @Autowired
    public WorkspaceController(WorkspaceService workspaceService) {
        this.workspaceService = workspaceService;
    }

    @GetMapping
    public ResponseEntity<List<WorkspaceDto>> getAll(@AuthenticationPrincipal JwtUser jwtUser) {
        List<Workspace> workspaces = workspaceService.getAll(jwtUser);

        List<WorkspaceDto> response = workspaces.stream()
                .map(WorkspaceDto::fromWorkspace)
                .collect(Collectors.toList());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<WorkspaceDto> getWorkspace(@PathVariable(name = "id") Long id,
                                                     @AuthenticationPrincipal JwtUser jwtUser) {
        Workspace workspace = workspaceService.findById(id, jwtUser);
        WorkspaceDto response = WorkspaceDto.fromWorkspace(workspace);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<WorkspaceDto> storeWorkspace(@Valid @RequestBody WorkspaceDto workspaceDto,
                                                       @AuthenticationPrincipal JwtUser jwtUser) {
        Workspace workspace = workspaceService.save(workspaceDto.toWorkspace(), jwtUser);
        WorkspaceDto response = WorkspaceDto.fromWorkspace(workspace);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseDto deleteWorkspace(@PathVariable(name = "id") Long workspaceId,
                                       @AuthenticationPrincipal JwtUser jwtUser) {
        return workspaceService.delete(workspaceId, jwtUser);
    }
}
