package com.SE3_NachhilfeApp.Contract;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table
public class Contract {
    @Id
    private UUID id = UUID.randomUUID();
    private UUID tutorID;
    private UUID schoolerID;
    private UUID subjectID;
    private boolean deleted;

    //Ctor
    public Contract() {}
    public Contract(UUID id, UUID tutorID, UUID schoolerID, UUID subjectID, boolean deleted) {
        this.id = id;
        this.tutorID = tutorID;
        this.schoolerID = schoolerID;
        this.subjectID = subjectID;
        this.deleted = deleted;
    }

    //toString
    @Override
    public String toString() {
        return "Contract{" +
                "id=" + id +
                ", tutorID='" + tutorID + '\'' +
                ", schoolerID='" + schoolerID + '\'' +
                ", subjectID='" + subjectID + '\'' +
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
    public UUID getTutorID() {
        return tutorID;
    }
    public void setTutorID(UUID toutorID) {
        this.tutorID = toutorID;
    }
    public UUID getSchoolerID() {
        return schoolerID;
    }
    public void setSchoolerID(UUID schoolerID) {
        this.schoolerID = schoolerID;
    }
    public UUID getSubjectID() {
        return subjectID;
    }
    public void setSubjectID(UUID subjectID) {
        this.subjectID = subjectID;
    }
    public boolean isDeleted() {
        return deleted;
    }
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
