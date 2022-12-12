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

    //Ctor
    public Submission() {}
    public Submission(LocalDate submissionDate) {
        this.submissionDate = submissionDate;
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
}
