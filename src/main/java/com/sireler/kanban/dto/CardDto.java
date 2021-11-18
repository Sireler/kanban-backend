package com.sireler.kanban.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sireler.kanban.model.Card;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CardDto {

    private Long id;

    @JsonProperty("list_id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long listId;

    @NotEmpty
    private String body;

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
