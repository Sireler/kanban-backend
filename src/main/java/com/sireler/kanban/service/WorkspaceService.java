package com.sireler.kanban.service;

import com.sireler.kanban.model.Workspace;

import java.util.List;

public interface WorkspaceService {

    List<Workspace> getAll();

    List<Workspace> findByName(String name);

    Workspace findById(Long id);

    void delete(Long id);
}
