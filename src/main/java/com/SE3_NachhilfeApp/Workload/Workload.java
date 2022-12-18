package com.SE3_NachhilfeApp.Workload;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table
public class Workload {
    @Id
    private UUID id = UUID.randomUUID();
    private UUID assignmentID;
    private UUID schoolerID;
    private UUID toutorID;
    private UUID submissionID;
    private LocalDate dueDate;
    private boolean deleted;

    //Ctor
    public Workload() {}
    public Workload(UUID assignmentID, UUID schoolerID, UUID toutorID, UUID submissionID, LocalDate dueDate, boolean deleted) {
        this.assignmentID = assignmentID;
        this.schoolerID = schoolerID;
        this.submissionID = submissionID;
        this.toutorID = toutorID;
        this.dueDate = dueDate;
        this.deleted = false;
    }

    //toString
    @Override
    public String toString() {
        return "Workload{" +
                "id=" + id +
                ", assignmentID='" + assignmentID + '\'' +
                ", userID='" + schoolerID + '\'' +
                ", submissionID='" + submissionID + '\'' +
                ", dueDate='" + dueDate + '\'' +
                '}';
    }

    //Get Set
    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public UUID getAssignmentID() {
        return assignmentID;
    }
    public void setAssignmentID(UUID assignmentID) {
        this.assignmentID = assignmentID;
    }
    public UUID getSchoolerID() {
        return schoolerID;
    }
    public void setSchoolerID(UUID userID) {
        this.schoolerID = userID;
    }
    public UUID getSubmissionID() {
        return submissionID;
    }
    public void setSubmissionID(UUID submissionID) {
        this.submissionID = submissionID;
    }
    public LocalDate getDueDate() {
        return dueDate;
    }
    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
    public UUID getToutorID() {
        return toutorID;
    }
    public void setToutorID(UUID toutorID) {
        this.toutorID = toutorID;
    }
    public boolean isDeleted() {
        return deleted;
    }
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
