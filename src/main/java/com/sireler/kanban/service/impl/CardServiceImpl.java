package com.sireler.kanban.service.impl;

import com.sireler.kanban.dto.ResponseDto;
import com.sireler.kanban.exception.KanbanApiException;
import com.sireler.kanban.model.Card;
import com.sireler.kanban.model.List;
import com.sireler.kanban.model.User;
import com.sireler.kanban.model.Workspace;
import com.sireler.kanban.repository.CardRepository;
import com.sireler.kanban.repository.ListRepository;
import com.sireler.kanban.repository.UserRepository;
import com.sireler.kanban.repository.WorkspaceRepository;
import com.sireler.kanban.security.jwt.JwtUser;
import com.sireler.kanban.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;

    private final UserRepository userRepository;

    private final ListRepository listRepository;

    private final WorkspaceRepository workspaceRepository;

    @Autowired
    public CardServiceImpl(
            CardRepository cardRepository,
            UserRepository userRepository,
            ListRepository listRepository,
            WorkspaceRepository workspaceRepository
    ) {
        this.cardRepository = cardRepository;
        this.userRepository = userRepository;
        this.listRepository = listRepository;
        this.workspaceRepository = workspaceRepository;
    }

    @Override
    public Card save(Card card, Long listId, JwtUser jwtUser) {
        User user = userRepository.findByUsername(jwtUser.getUsername());
        List list = listRepository.findById(listId)
                .orElseThrow(() -> new KanbanApiException(HttpStatus.NOT_FOUND, "List not found with id: " + listId));

        if (list.getWorkspace().getUser().getId().equals(user.getId())) {
            card.setList(list);
            return cardRepository.save(card);
        }

        throw new KanbanApiException(HttpStatus.UNAUTHORIZED, "You dont have permission to access");
    }

    @Override
    public ResponseDto delete(Long workspaceId, Long cardId, JwtUser jwtUser) {
        User user = userRepository.findByUsername(jwtUser.getUsername());
        Workspace workspace = workspaceRepository.findById(workspaceId)
                .orElseThrow(() -> new KanbanApiException(HttpStatus.NOT_FOUND, "Workspace not found with id: " + workspaceId));

        if (workspace.getUser().getId().equals(user.getId())) {
            cardRepository.deleteById(cardId);
            return new ResponseDto(HttpStatus.OK, true, "Card deleted successfully");
        }

        throw new KanbanApiException(HttpStatus.UNAUTHORIZED, "You dont have permission to access");
    }
}
