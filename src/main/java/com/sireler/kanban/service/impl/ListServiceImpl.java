package com.sireler.kanban.service.impl;

import com.sireler.kanban.model.List;
import com.sireler.kanban.repository.ListRepository;
import com.sireler.kanban.service.ListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ListServiceImpl implements ListService {

    private ListRepository listRepository;

    @Autowired
    public ListServiceImpl(ListRepository listRepository) {
        this.listRepository = listRepository;
    }

    @Override
    public List findById(Long id) {
        return listRepository.findById(id)
                .orElse(null);
    }

    @Override
    public List create(List list) {
        return listRepository.save(list);
    }

    @Override
    public void delete(Long id) {
        listRepository.deleteById(id);
    }

    @Override
    public java.util.List<List> findAllByWorkspaceId(Long id) {
        return listRepository.findAllByWorkspaceId(id);
    }
}
