package com.sireler.kanban.repository;

import com.sireler.kanban.model.Workspace;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkspaceRepository extends JpaRepository<Workspace, Long> {
    List<Workspace> findByName(String name);
}
