package com.sireler.kanban.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@Table(name = "workspaces")
public class Workspace extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @ManyToMany(mappedBy = "workspaces", fetch = FetchType.LAZY)
    private java.util.List<User> users;

    @OneToMany(mappedBy = "workspace")
    private java.util.List<List> lists;
}
