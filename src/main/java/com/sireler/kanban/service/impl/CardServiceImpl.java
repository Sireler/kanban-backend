package com.sireler.kanban.service.impl;

import com.sireler.kanban.model.Card;
import com.sireler.kanban.repository.CardRepository;
import com.sireler.kanban.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardServiceImpl implements CardService {

    private CardRepository cardRepository;

    @Autowired
    public CardServiceImpl(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Override
    public Card findById(Long id) {
        return cardRepository.findById(id)
                .orElse(null);
    }

    @Override
    public List<Card> findAllByListId(Long id) {
        return cardRepository.findAllByListId(id);
    }

    @Override
    public Card create(Card card) {
        return cardRepository.save(card);
    }

    @Override
    public void delete(Long id) {
        cardRepository.deleteById(id);
    }
}
