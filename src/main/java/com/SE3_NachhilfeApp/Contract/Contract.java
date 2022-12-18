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
    private UUID toutorID;
    private UUID schoolerID;
    private UUID subjectID;

    //Ctor
    public Contract() {}
    public Contract(UUID toutorID, UUID schoolerID, UUID subjectID) {
        this.toutorID = toutorID;
        this.schoolerID = schoolerID;
        this.subjectID = subjectID;
    }

    //toString
    @Override
    public String toString() {
        return "Contract{" +
                "id=" + id +
                ", toutorID='" + toutorID + '\'' +
                ", schoolerID='" + schoolerID + '\'' +
                ", subjectID='" + subjectID + '\'' +
                '}';
    }

    //Get Set
    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public UUID getToutorID() {
        return toutorID;
    }
    public void setToutorID(UUID toutorID) {
        this.toutorID = toutorID;
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
}
