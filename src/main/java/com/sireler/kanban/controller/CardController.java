package com.sireler.kanban.controller;

import com.sireler.kanban.dto.CardDto;
import com.sireler.kanban.model.Card;
import com.sireler.kanban.model.List;
import com.sireler.kanban.security.jwt.JwtUser;
import com.sireler.kanban.service.CardService;
import com.sireler.kanban.service.ListService;
import com.sireler.kanban.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/v1/workspaces/{workspaceId}/lists/{listId}/cards")
public class CardController {

    private final CardService cardService;

    private final ListService listService;

    public CardController(CardService cardService, ListService listService, UserService userService) {
        this.cardService = cardService;
        this.listService = listService;
    }

    @PostMapping
    public ResponseEntity<CardDto> storeCard(@Valid @RequestBody CardDto cardDto,
                                             @PathVariable(name = "workspaceId") Long workspaceId,
                                             @PathVariable(name = "listId") Long listId,
                                             @AuthenticationPrincipal JwtUser jwtUser) {
        List list = listService.findById(listId, workspaceId, jwtUser);
        Card card = CardDto.toCard(cardDto);
        card.setList(list);
        cardService.create(card);
        CardDto result = CardDto.fromCard(card);

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }
}
