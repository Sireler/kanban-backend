package com.sireler.kanban.controller;

import com.sireler.kanban.dto.ListDto;
import com.sireler.kanban.dto.WorkspaceDto;
import com.sireler.kanban.model.List;
import com.sireler.kanban.model.User;
import com.sireler.kanban.model.Workspace;
import com.sireler.kanban.security.jwt.JwtUser;
import com.sireler.kanban.service.ListService;
import com.sireler.kanban.service.UserService;
import com.sireler.kanban.service.WorkspaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/v1/workspaces/{id}/lists")
public class ListController {

    private final ListService listService;

    private final UserService userService;

    private final WorkspaceService workspaceService;

    @Autowired
    public ListController(ListService listService, UserService userService, WorkspaceService workspaceService) {
        this.listService = listService;
        this.userService = userService;
        this.workspaceService = workspaceService;
    }

    @GetMapping
    public ResponseEntity<?> getAllLists(@PathVariable(name = "id") Long workspaceId,
                                                            @AuthenticationPrincipal JwtUser jwtUser) {
        User user = userService.findByUsername(jwtUser.getUsername());
        java.util.List<List> workspaceLists = listService.findAllByWorkspaceId(workspaceId);
//        java.util.List<ListDto> workspaceDtos = workspaceLists.stream()
//                .map(ListDto::fromList)
//                .collect(Collectors.toList());
        return ResponseEntity.ok(workspaceLists);
    }

    @PostMapping
    public ResponseEntity<ListDto> storeList(@RequestBody ListDto listDto,
                                             @PathVariable(name = "id") Long workspaceId,
                                             @AuthenticationPrincipal JwtUser jwtUser) {
        User user = userService.findByUsername(jwtUser.getUsername());
        Workspace workspace = workspaceService.findById(listDto.getWorkspaceId());
        List workspaceList = ListDto.toList(listDto);
        workspaceList.setWorkspace(workspace);
        listService.create(workspaceList);

        return new ResponseEntity<>(ListDto.fromList(workspaceList), HttpStatus.CREATED);
    }
}
