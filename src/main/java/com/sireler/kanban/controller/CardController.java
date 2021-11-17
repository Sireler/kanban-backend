package com.sireler.kanban.controller;

import com.sireler.kanban.dto.CardDto;
import com.sireler.kanban.dto.ListDto;
import com.sireler.kanban.model.Card;
import com.sireler.kanban.model.List;
import com.sireler.kanban.model.User;
import com.sireler.kanban.model.Workspace;
import com.sireler.kanban.security.jwt.JwtUser;
import com.sireler.kanban.service.CardService;
import com.sireler.kanban.service.ListService;
import com.sireler.kanban.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/workspaces/{workspaceId}/lists/{listId}/cards")
public class CardController {

    private final CardService cardService;

    private final ListService listService;

    private final UserService userService;

    public CardController(CardService cardService, ListService listService, UserService userService) {
        this.cardService = cardService;
        this.listService = listService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<CardDto> storeCard(@RequestBody CardDto cardDto,
                                          @PathVariable(name = "listId") Long listId,
                                          @AuthenticationPrincipal JwtUser jwtUser) {
        User user = userService.findByUsername(jwtUser.getUsername());
        List list = listService.findById(listId);
        Card card = CardDto.toCard(cardDto);
        card.setList(list);
        cardService.create(card);

        return new ResponseEntity<>(CardDto.fromCard(card), HttpStatus.CREATED);
    }
}
