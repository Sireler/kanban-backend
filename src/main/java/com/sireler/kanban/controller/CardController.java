package com.sireler.kanban.controller;

import com.sireler.kanban.dto.CardDto;
import com.sireler.kanban.dto.ResponseDto;
import com.sireler.kanban.model.Card;
import com.sireler.kanban.security.jwt.JwtUser;
import com.sireler.kanban.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/v1/workspaces/{workspaceId}/lists/{listId}/cards")
public class CardController {

    private final CardService cardService;

    @Autowired
    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @PostMapping
    public ResponseEntity<CardDto> storeCard(@Valid @RequestBody CardDto cardDto,
                                             @PathVariable(name = "workspaceId") Long workspaceId,
                                             @PathVariable(name = "listId") Long listId,
                                             @AuthenticationPrincipal JwtUser jwtUser) {
        Card card = CardDto.toCard(cardDto);
        cardService.save(card, listId, jwtUser);
        CardDto response = CardDto.fromCard(card);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("{cardId}")
    public ResponseDto deleteWorkspace(@PathVariable(name = "cardId") Long cardId,
                                       @PathVariable(name = "workspaceId") Long workspaceId,
                                       @AuthenticationPrincipal JwtUser jwtUser) {
        return cardService.delete(workspaceId, cardId, jwtUser);
    }
}
