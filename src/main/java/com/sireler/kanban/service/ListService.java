package com.sireler.kanban.service;

import com.sireler.kanban.dto.ResponseDto;
import com.sireler.kanban.model.List;
import com.sireler.kanban.security.jwt.JwtUser;

public interface ListService {

    List findById(Long id, Long workspaceId, JwtUser jwtUser);

    java.util.List<List> findByWorkspaceId(Long id, JwtUser jwtUser);

    List save(List list, Long workspaceId, JwtUser jwtUser);

    ResponseDto delete(Long id, Long workspaceId, JwtUser jwtUser);
}
