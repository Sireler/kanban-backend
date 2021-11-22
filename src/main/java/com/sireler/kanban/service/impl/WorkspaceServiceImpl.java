package com.sireler.kanban.service.impl;

import com.sireler.kanban.dto.ResponseDto;
import com.sireler.kanban.exception.KanbanApiException;
import com.sireler.kanban.model.User;
import com.sireler.kanban.model.Workspace;
import com.sireler.kanban.repository.UserRepository;
import com.sireler.kanban.repository.WorkspaceRepository;
import com.sireler.kanban.security.jwt.JwtUser;
import com.sireler.kanban.service.WorkspaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkspaceServiceImpl implements WorkspaceService {

    private final WorkspaceRepository workspaceRepository;

    private final UserRepository userRepository;

    @Autowired
    public WorkspaceServiceImpl(WorkspaceRepository workspaceRepository, UserRepository userRepository) {
        this.workspaceRepository = workspaceRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Workspace> getAll(JwtUser user) {
        return workspaceRepository.findByUserId(user.getId());
    }

    @Override
    public List<Workspace> findByName(String name) {
        return workspaceRepository.findByName(name);
    }

    @Override
    public Workspace findById(Long id, JwtUser jwtUser) {
        User user = userRepository.findByUsername(jwtUser.getUsername());
        Workspace workspace = workspaceRepository.findById(id)
                .orElseThrow(() -> new KanbanApiException(HttpStatus.NOT_FOUND, "Workspace not found with id: " + id));

        if (workspace.getUser().getId().equals(user.getId())) {
            return workspace;
        }

        throw new KanbanApiException(HttpStatus.UNAUTHORIZED, "You dont have permission to access");
    }

    @Override
    public Workspace save(Workspace workspace, JwtUser jwtUser) {
        User user = userRepository.findByUsername(jwtUser.getUsername());
        workspace.setUser(user);

        return workspaceRepository.save(workspace);
    }

    @Override
    public ResponseDto delete(Long id, JwtUser jwtUser) {
        User user = userRepository.findByUsername(jwtUser.getUsername());
        Workspace workspace = workspaceRepository.findById(id)
                .orElseThrow(() -> new KanbanApiException(HttpStatus.NOT_FOUND, "Workspace not found with id: " + id));

        if (workspace.getUser().getId().equals(user.getId())) {
            workspaceRepository.deleteById(id);
            return new ResponseDto(HttpStatus.OK, true, "Workspace deleted successfully");
        }

        return new ResponseDto(HttpStatus.UNAUTHORIZED, false, "You dont have permission to access");
    }
}
