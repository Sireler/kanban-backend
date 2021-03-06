package com.sireler.kanban.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Setter
@Getter
@Table(name = "roles")
public class Role extends BaseEntity {

    @Column(name = "name")
    private String name;
}
