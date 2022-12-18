package com.SE3_NachhilfeApp.Subjects;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table
public class Subject {
    @Id
    private UUID id = UUID.randomUUID();
    private String name;
    private boolean deleted;

    //Ctor
    public Subject() {}

    //toString
    @Override
    public String toString() {
        return "Subjects{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    //Get Set
    public UUID getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public void setId(UUID id) {
        this.id = id;
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
