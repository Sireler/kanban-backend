package com.sireler.kanban.controller;

import com.sireler.kanban.dto.ListDto;
import com.sireler.kanban.dto.ResponseDto;
import com.sireler.kanban.model.List;
import com.sireler.kanban.security.jwt.JwtUser;
import com.sireler.kanban.service.ListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/v1/workspaces/{workspaceId}/lists")
public class ListController {

    private final ListService listService;

    @Autowired
    public ListController(ListService listService) {
        this.listService = listService;
    }

    @GetMapping
    public ResponseEntity<java.util.List<ListDto>> getAll(@PathVariable(name = "workspaceId") Long workspaceId,
                                                          @AuthenticationPrincipal JwtUser jwtUser) {
        java.util.List<List> lists = listService.findByWorkspaceId(workspaceId, jwtUser);
        java.util.List<ListDto> response = lists.stream()
                .map(ListDto::fromList)
                .collect(Collectors.toList());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("{listId}")
    public ResponseEntity<ListDto> getList(@PathVariable(name = "workspaceId") Long workspaceId,
                                           @PathVariable(name = "listId") Long listId,
                                           @AuthenticationPrincipal JwtUser jwtUser) {
        List list = listService.findById(listId, workspaceId, jwtUser);
        ListDto response = ListDto.fromList(list);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ListDto> storeList(@Valid @RequestBody ListDto listDto,
                                             @PathVariable(name = "workspaceId") Long workspaceId,
                                             @AuthenticationPrincipal JwtUser jwtUser) {
        List list = listService.save(listDto.toList(), workspaceId, jwtUser);
        ListDto response = ListDto.fromList(list);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("{listId}")
    public ResponseDto deleteList(@PathVariable(name = "workspaceId") Long workspaceId,
                                  @PathVariable(name = "listId") Long listId,
                                  @AuthenticationPrincipal JwtUser jwtUser) {
        return listService.delete(listId, workspaceId, jwtUser);
    }
}
