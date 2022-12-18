package com.SE3_NachhilfeApp.Submission;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table
public class Submission {
    @Id
    private UUID id = UUID.randomUUID();
    private LocalDate submissionDate;
    private boolean deleted;

    //Ctor
    public Submission() {}
    public Submission(LocalDate submissionDate, boolean deleted) {
        this.submissionDate = submissionDate;
        this.deleted = false;
    }

    //toString
    @Override
    public String toString() {
        return "Workload{" +
                "id=" + id +
                ", submissionDate='" + submissionDate + '\'' +
                '}';
    }

    //Get Set
    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public LocalDate getSubmissionDate() {
        return submissionDate;
    }
    public void setSubmissionDate(LocalDate submissionDate) {
        this.submissionDate = submissionDate;
    }
    public boolean isDeleted() {
        return deleted;
    }
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
