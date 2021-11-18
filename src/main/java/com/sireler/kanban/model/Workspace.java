package com.sireler.kanban.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "workspaces")
public class Workspace extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @ManyToMany(mappedBy = "workspaces", fetch = FetchType.LAZY)
    private List<User> users;
}
