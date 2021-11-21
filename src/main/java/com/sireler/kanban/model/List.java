package com.sireler.kanban.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@Table(name = "lists")
public class List extends BaseEntity {

    @Column(name = "title")
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workspace_id")
    @JsonIgnoreProperties({"lists", "users", "workspace"})
    private Workspace workspace;

    @OneToMany(mappedBy = "list")
    @JsonIgnoreProperties("list")
    private java.util.List<Card> cards;
}
