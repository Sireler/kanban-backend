package com.sireler.kanban.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sireler.kanban.model.Card;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CardDto {

    private Long id;

    @JsonProperty("list_id")
    private Long listId;

    private String body;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getListId() {
        return listId;
    }

    public void setListId(Long listId) {
        this.listId = listId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public static Card toCard(CardDto cardDto) {
        Card card = new Card();
        card.setId(cardDto.getId());
        card.setBody(cardDto.getBody());
        return card;
    }

    public static CardDto fromCard(Card card) {
        CardDto cardDto = new CardDto();
        cardDto.setId(card.getId());
        cardDto.setBody(card.getBody());
        cardDto.setListId(card.getList().getId());
        return cardDto;
    }
}
