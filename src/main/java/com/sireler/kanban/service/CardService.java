package com.sireler.kanban.service;

import com.sireler.kanban.dto.ResponseDto;
import com.sireler.kanban.model.Card;
import com.sireler.kanban.security.jwt.JwtUser;

public interface CardService {

    Card save(Card card, Long listId, JwtUser jwtUser);

    ResponseDto delete(Long workspaceId, Long cardId, JwtUser jwtUser);
}
