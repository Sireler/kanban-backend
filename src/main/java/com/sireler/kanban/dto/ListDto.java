package com.sireler.kanban.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sireler.kanban.model.List;
import com.sireler.kanban.model.Workspace;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ListDto {

    private Long id;

    private String title;

    @JsonProperty("workspace_id")
    private Long workspaceId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getWorkspaceId() {
        return workspaceId;
    }

    public void setWorkspaceId(Long workspaceId) {
        this.workspaceId = workspaceId;
    }

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
