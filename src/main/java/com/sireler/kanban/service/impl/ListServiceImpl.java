package com.sireler.kanban.service.impl;

import com.sireler.kanban.dto.ResponseDto;
import com.sireler.kanban.exception.KanbanApiException;
import com.sireler.kanban.model.List;
import com.sireler.kanban.model.User;
import com.sireler.kanban.model.Workspace;
import com.sireler.kanban.repository.ListRepository;
import com.sireler.kanban.repository.UserRepository;
import com.sireler.kanban.repository.WorkspaceRepository;
import com.sireler.kanban.security.jwt.JwtUser;
import com.sireler.kanban.service.ListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ListServiceImpl implements ListService {

    private final ListRepository listRepository;

    private final UserRepository userRepository;

    private final WorkspaceRepository workspaceRepository;

    @Autowired
    public ListServiceImpl(ListRepository listRepository,
                           UserRepository userRepository,
                           WorkspaceRepository workspaceRepository) {
        this.listRepository = listRepository;
        this.userRepository = userRepository;
        this.workspaceRepository = workspaceRepository;

    }

    @Override
    public List findById(Long id, Long workspaceId, JwtUser jwtUser) {
        User user = userRepository.findByUsername(jwtUser.getUsername());
        List list = listRepository.findById(id)
                .orElseThrow(() -> new KanbanApiException(HttpStatus.NOT_FOUND, "List not found with id: " + id));

        if (list.getWorkspace().getUser().getId().equals(user.getId())) {
            if (list.getWorkspace().getId().equals(workspaceId)) {
                return list;
            }
        }

        throw new KanbanApiException(HttpStatus.UNAUTHORIZED, "You dont have permission to access");
    }

    @Override
    public java.util.List<List> findByWorkspaceId(Long id, JwtUser jwtUser) {
        User user = userRepository.findByUsername(jwtUser.getUsername());
        Workspace workspace = workspaceRepository.findById(id)
                .orElseThrow(() -> new KanbanApiException(HttpStatus.NOT_FOUND, "Workspace not found with id: " + id));

        if (workspace.getUser().getId().equals(user.getId())) {
            return listRepository.findByWorkspaceId(id);
        }

        throw new KanbanApiException(HttpStatus.UNAUTHORIZED, "You dont have permission to access");
    }

    @Override
    public List save(List list, Long workspaceId, JwtUser jwtUser) {
        User user = userRepository.findByUsername(jwtUser.getUsername());
        Workspace workspace = workspaceRepository.findById(workspaceId)
                .orElseThrow(() -> new KanbanApiException(HttpStatus.NOT_FOUND, "Workspace not found with id: " + workspaceId));

        if (workspace.getUser().getId().equals(user.getId())) {
            list.setWorkspace(workspace);
            return listRepository.save(list);
        }

        throw new KanbanApiException(HttpStatus.UNAUTHORIZED, "You dont have permission to access");
    }

    @Override
    public ResponseDto delete(Long id, Long workspaceId, JwtUser jwtUser) {
        User user = userRepository.findByUsername(jwtUser.getUsername());
        List list = listRepository.findById(id)
                .orElseThrow(() -> new KanbanApiException(HttpStatus.NOT_FOUND, "Workspace not found with id: " + id));

        if (list.getWorkspace().getUser().getId().equals(user.getId())) {
            if (list.getWorkspace().getId().equals(workspaceId)) {
                listRepository.deleteById(id);
                return new ResponseDto(HttpStatus.OK, true, "List deleted successfully");
            }
        }

        throw new KanbanApiException(HttpStatus.UNAUTHORIZED, "You dont have permission to access");
    }
}
