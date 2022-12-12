package com.SE3_NachhilfeApp.Solution;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table
public class Solution {
    @Id
    private UUID id = UUID.randomUUID();
    private UUID taskID;
    private UUID submisionID;
    private String solutionText;

    //Ctor
    public Solution() {}
    public Solution(UUID taskID, UUID submisionID, String solutionText) {
        this.taskID = taskID;
        this.submisionID = submisionID;
        this.solutionText = solutionText;
    }

    //toString
    @Override
    public String toString() {
        return "Solution{" +
                "id=" + id +
                ", taskID='" + taskID + '\'' +
                ", solutionText='" + solutionText + '\'' +
                '}';
    }

    //Get Set
    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public UUID getTaskID() {
        return taskID;
    }
    public void setTaskID(UUID taskID) {
        this.taskID = taskID;
    }
    public String getSolutionText() {
        return solutionText;
    }
    public void setSolutionText(String solutionText) {
        this.solutionText = solutionText;
    }
    public UUID getSubmisionID() {
        return submisionID;
    }
    public void setSubmisionID(UUID submisionID) {
        this.submisionID = submisionID;
    }
}
