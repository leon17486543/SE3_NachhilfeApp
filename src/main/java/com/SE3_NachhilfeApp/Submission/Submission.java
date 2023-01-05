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
    public Submission(UUID id, LocalDate submissionDate, boolean deleted) {
        this.id = id;
        this.submissionDate = submissionDate;
        this.deleted = deleted;
    }

    //toString
    @Override
    public String toString() {
        return "Submission{" +
                "id=" + id +
                ", submissionDate='" + submissionDate + '\'' +
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
