package com.sireler.kanban.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sireler.kanban.model.List;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ListDto {

    private Long id;

    @NotEmpty
    private String title;

    public List toList() {
        List list = new List();
        list.setId(id);
        list.setTitle(title);

        return list;
    }

    public static ListDto fromList(List list) {
        ListDto listDto = new ListDto();
        listDto.id = list.getId();
        listDto.title = list.getTitle();

        return listDto;
    }
}
