package com.sireler.kanban.service.impl;

import com.sireler.kanban.model.Workspace;
import com.sireler.kanban.repository.WorkspaceRepository;
import com.sireler.kanban.service.WorkspaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkspaceServiceImpl implements WorkspaceService {

    @Autowired
    private WorkspaceRepository workspaceRepository;

    @Override
    public List<Workspace> getAll() {
        return workspaceRepository.findAll();
    }

    @Override
    public List<Workspace> findByName(String name) {
        return workspaceRepository.findByName(name);
    }

    @Override
    public Workspace findById(Long id) {
        return workspaceRepository.findById(id)
                .orElse(null);
    }

    @Override
    public Workspace create(Workspace workspace) {
        return workspaceRepository.save(workspace);
    }

    @Override
    public void delete(Long id) {
        workspaceRepository.deleteById(id);
    }
}
