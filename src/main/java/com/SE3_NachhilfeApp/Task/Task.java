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
    private String userSolution;

    public Task() {
    }

    public Task(UUID assignmentID, String name) {
        this.name = name;
        this.assignmentID = assignmentID;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", assignmentID='" + assignmentID + '\'' +
                '}';
    }

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

    public String getUserSolution() {
        return userSolution;
    }

    public void setUserSolution(String userSolution) {
        this.userSolution = userSolution;
    }
}
