package com.sireler.kanban.model;

import javax.persistence.*;

@Entity
@Table(name = "cards")
public class Card extends BaseEntity {

    private String body;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "list_id", nullable = false)
    private List list;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }
}
