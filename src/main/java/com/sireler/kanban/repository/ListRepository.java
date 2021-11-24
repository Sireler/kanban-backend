package com.sireler.kanban.repository;

import com.sireler.kanban.model.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ListRepository extends JpaRepository<List, Long> {
    java.util.List<List> findByWorkspaceId(Long id);
}
