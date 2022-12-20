package com.SE3_NachhilfeApp.Task;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;

@Entity
@Table
public class Task {
    @Id
    private UUID id = UUID.randomUUID();
    private UUID assignmentID;
    private String name;
    private String correctSolution;
    private boolean deleted;

    //Ctor
    public Task() {
    }
    public Task(UUID id, UUID assignmentID, String name, String correctSolution, boolean deleted) {
        this.id = id;
        this.name = name;
        this.assignmentID = assignmentID;
        this.correctSolution = correctSolution;
        this.deleted = false;
    }

    //toString
    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", assignmentID='" + assignmentID + '\'' +
                ", name='" + name + '\'' +
                ", correctSolution='" + correctSolution + '\'' +
                ", deleted='" + deleted + '\'' +
                '}';
    }

    //Get Set
    public UUID getAssignmentID() {
        return assignmentID;
    }
    public void setAssignmentID(UUID assignmentID) {
        this.assignmentID = assignmentID;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getCorrectSolution() {
        return correctSolution;
    }
    public void setCorrectSolution(String userSolution) {
        this.correctSolution = userSolution;
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
