package com.sireler.kanban.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "workspaces")
public class Workspace extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "workspace_users",
            joinColumns = {@JoinColumn(name = "workspace_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")}
    )
    private List<Workspace> workspaces;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Workspace> getWorkspaces() {
        return workspaces;
    }

    public void setWorkspaces(List<Workspace> workspaces) {
        this.workspaces = workspaces;
    }

    @Override
    public String toString() {
        return "Workspace{" +
                "id=" + id +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", name='" + name + '\'' +
                ", workspaces=" + workspaces +
                '}';
    }
}
