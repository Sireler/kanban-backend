package com.sireler.kanban.service;

import com.sireler.kanban.model.List;
import com.sireler.kanban.model.Workspace;

public interface ListService {

    List findById(Long id);

    java.util.List<List> findAllByWorkspaceId(Long id);

    List create(List list);

    void delete(Long id);
}
