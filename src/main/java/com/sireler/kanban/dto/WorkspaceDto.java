package com.sireler.kanban.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sireler.kanban.model.Workspace;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WorkspaceDto {

    private Long id;

    @NotEmpty
    private String name;

    public Workspace toWorkspace() {
        Workspace workspace = new Workspace();
        workspace.setId(id);
        workspace.setName(name);

        return workspace;
    }

    public static WorkspaceDto fromWorkspace(Workspace workspace) {
        WorkspaceDto workspaceDto = new WorkspaceDto();
        workspaceDto.setId(workspace.getId());
        workspaceDto.setName(workspace.getName());

        return workspaceDto;
    }
}
