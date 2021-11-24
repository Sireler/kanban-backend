package com.sireler.kanban.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.sireler.kanban.model.List;
import com.sireler.kanban.model.Workspace;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WorkspaceDto {

    private Long id;

    @NotEmpty
    private String name;

    @JsonIgnore
    private java.util.List<List> lists;

    public Workspace toWorkspace() {
        Workspace workspace = new Workspace();
        workspace.setId(id);
        workspace.setName(name);
        workspace.setLists(lists);

        return workspace;
    }

    public static WorkspaceDto fromWorkspace(Workspace workspace) {
        WorkspaceDto workspaceDto = new WorkspaceDto();
        workspaceDto.setId(workspace.getId());
        workspaceDto.setName(workspace.getName());
        workspaceDto.setLists(workspace.getLists());

        return workspaceDto;
    }
}
