package com.sireler.kanban.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "lists")
public class List extends BaseEntity {

    @Column(name = "title")
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workspace_id", nullable = false)
    private Workspace workspace;
}
