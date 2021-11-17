package com.sireler.kanban.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "workspaces")
public class Workspace extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @ManyToMany(mappedBy = "workspaces", fetch = FetchType.LAZY)
    private List<User> users;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
