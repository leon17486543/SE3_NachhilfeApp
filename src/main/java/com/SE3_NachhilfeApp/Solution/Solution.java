package com.SE3_NachhilfeApp.Solution;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table
public class Solution {
    @Id
    private UUID id = UUID.randomUUID();
    private UUID taskID;
    private UUID submissionID;
    private String solutionText;
    private boolean deleted;

    //Ctor
    public Solution() {}
    public Solution(UUID taskID, UUID submissionID, String solutionText, boolean deleted) {
        this.taskID = taskID;
        this.submissionID = submissionID;
        this.solutionText = solutionText;
        this.deleted = false;
    }

    //toString
    @Override
    public String toString() {
        return "Solution{" +
                "id=" + id +
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
    public UUID getSubmissionID() {
        return submissionID;
    }
    public void setSubmissionID(UUID submisionID) {
        this.submissionID = submisionID;
    }
    public String getSolutionText() {
        return solutionText;
    }
    public void setSolutionText(String solutionText) {
        this.solutionText = solutionText;
    }
    public boolean isDeleted() {
        return deleted;
    }
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
