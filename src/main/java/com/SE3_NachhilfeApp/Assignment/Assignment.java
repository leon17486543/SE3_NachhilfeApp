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
    private LocalDate dueDate;

    public Assignment() {
    }

    public Assignment(UUID owner, UUID subject, String name, String description, LocalDate dueDate) {
        this.owner = owner;
        this.subject = subject;
        this.name = name;
        this.description = description;
        this.dueDate = dueDate;
    }


    @Override
    public String toString() {
        return "ASSIGNMENTS{" +
                "id=" + id +
                ", owner='" + owner + '\'' +
                ", subject='" + subject + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", dueDate=" + dueDate +
                '}';
    }

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

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
}
