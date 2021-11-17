package com.sireler.kanban.repository;

import com.sireler.kanban.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardRepository extends JpaRepository<Card, Long> {
    List<Card> findAllByListId(Long id);
}
