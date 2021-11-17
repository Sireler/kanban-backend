package com.sireler.kanban.service;

import com.sireler.kanban.model.Card;

import java.util.List;

public interface CardService {

    Card findById(Long id);

    List<Card> findAllByListId(Long id);

    Card create(Card card);

    void delete(Long id);
}
