package com.SE3_NachhilfeApp.Assignment;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table
public class Assignment {
    @Id
    private UUID id = UUID.randomUUID();
    private UUID owner;
    private UUID subject;
    private String name;
    private String description;
    private boolean deleted;

    //Ctor
    public Assignment() {}
    public Assignment(UUID id, UUID owner, UUID subject, String name, String description, boolean deleted) {
        this.id = id;
        this.owner = owner;
        this.subject = subject;
        this.name = name;
        this.description = description;
        this.deleted = deleted;
    }

    //toString
    @Override
    public String toString() {
        return "ASSIGNMENTS{" +
                "id=" + id +
                ", owner='" + owner + '\'' +
                ", subject='" + subject + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", description='" + deleted + '\'' +
                '}';
    }

    //get set
    public UUID getOwner() {
        return owner;
    }
    public void setOwner(UUID owner) {
        this.owner = owner;
    }
    public UUID getSubject() {
        return subject;
    }
    public void setSubject(UUID subject) {
        this.subject = subject;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public boolean isDeleted() {
        return deleted;
    }
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
