package com.SE3_NachhilfeApp.Offer;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;

@Entity
@Table
public class Offer {
    @Id
    private UUID id = UUID.randomUUID();
    private UUID subjectID;
    private UUID memberID;

    //Ctor
    public Offer() {}
    public Offer(UUID subjectID, UUID memberID) {
        this.subjectID = subjectID;
        this.memberID = memberID;
    }

    //toString
    @Override
    public String toString() {
        return "Offer{" +
                "id=" + id +
                ", subjectID='" + subjectID + '\'' +
                ", memberID='" + memberID + '\'' +
                '}';
    }

    //Get Set
    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public UUID getSubjectID() {
        return subjectID;
    }
    public void setSubjectID(UUID subjectID) {
        this.subjectID = subjectID;
    }
    public UUID getMemberID() {
        return memberID;
    }
    public void setMemberID(UUID memberID) {
        this.memberID = memberID;
    }
}
