package com.sireler.kanban.controller;

import com.sireler.kanban.dto.ListDto;
import com.sireler.kanban.model.List;
import com.sireler.kanban.model.Workspace;
import com.sireler.kanban.security.jwt.JwtUser;
import com.sireler.kanban.service.ListService;
import com.sireler.kanban.service.WorkspaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/v1/workspaces/{id}/lists")
public class ListController {

    private final ListService listService;

    private final WorkspaceService workspaceService;

    @Autowired
    public ListController(ListService listService, WorkspaceService workspaceService) {
        this.listService = listService;
        this.workspaceService = workspaceService;
    }

    @GetMapping
    public ResponseEntity<java.util.List<ListDto>> getAll(@PathVariable(name = "id") Long workspaceId) {
        //TODO: check user permissions
        java.util.List<List> workspacesList = listService.findAllByWorkspaceId(workspaceId);
        java.util.List<ListDto> result = workspacesList.stream()
                .map(ListDto::fromList)
                .collect(Collectors.toList());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ListDto> storeList(@Valid @RequestBody ListDto listDto,
                                             @PathVariable(name = "id") Long workspaceId,
                                             @AuthenticationPrincipal JwtUser jwtUser) {
        listDto.setWorkspaceId(workspaceId);
        Workspace workspace = workspaceService.findById(listDto.getWorkspaceId(), jwtUser);
        List workspaceList = ListDto.toList(listDto);
        workspaceList.setWorkspace(workspace);
        listService.create(workspaceList);

        return new ResponseEntity<>(ListDto.fromList(workspaceList), HttpStatus.CREATED);
    }
}
