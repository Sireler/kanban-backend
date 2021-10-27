package com.sireler.kanban.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sireler.kanban.model.Workspace;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WorkspaceDto {

    private Long id;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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
