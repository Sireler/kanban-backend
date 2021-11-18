package com.sireler.kanban.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sireler.kanban.model.List;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ListDto {

    private Long id;

    @NotEmpty
    private String title;

    @JsonProperty("workspace_id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long workspaceId;

    public static List toList(ListDto listDto) {
        List list = new List();
        list.setId(listDto.getId());
        list.setTitle(listDto.getTitle());

        return list;
    }

    public static ListDto fromList(List list) {
        ListDto listDto = new ListDto();
        listDto.id = list.getId();
        listDto.title = list.getTitle();

        return listDto;
    }
}
