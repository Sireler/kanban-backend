package com.sireler.kanban.service;

import com.sireler.kanban.dto.ResponseDto;
import com.sireler.kanban.model.Workspace;
import com.sireler.kanban.security.jwt.JwtUser;

import java.util.List;

public interface WorkspaceService {

    List<Workspace> getAll(JwtUser user);

    List<Workspace> findByName(String name);

    Workspace findById(Long id, JwtUser jwtUser);

    Workspace save(Workspace workspace, JwtUser user);

    ResponseDto delete(Long id, JwtUser user);
}
