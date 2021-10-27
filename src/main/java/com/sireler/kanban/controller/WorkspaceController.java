package com.sireler.kanban.controller;

import com.sireler.kanban.dto.WorkspaceDto;
import com.sireler.kanban.model.Workspace;
import com.sireler.kanban.service.WorkspaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/workspaces/")
public class WorkspaceController {

    private final WorkspaceService workspaceService;

    @Autowired
    public WorkspaceController(WorkspaceService workspaceService) {
        this.workspaceService = workspaceService;
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
}
