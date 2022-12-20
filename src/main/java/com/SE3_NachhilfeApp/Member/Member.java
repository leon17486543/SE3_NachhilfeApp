package com.SE3_NachhilfeApp.Member;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table
public class Member {
    @Id
    private UUID id = UUID.randomUUID();
    private String name;
    private boolean deleted;

    //Ctor
    public Member() {}

    public Member(UUID id, String name, boolean deleted) {
        this.id = id;
        this.name = name;
        this.deleted = deleted;
    }

    //toString
    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", deleted='" + deleted + '\'' +
                '}';
    }

    //Get Set
    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public boolean isDeleted() {
        return deleted;
    }
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
