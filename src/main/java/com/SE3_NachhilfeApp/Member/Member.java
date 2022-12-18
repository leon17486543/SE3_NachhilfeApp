package com.SE3_NachhilfeApp.Member;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table
public class Member {
    @Id
    private UUID id = UUID.randomUUID();
    private String name;
    private boolean needsHelp;
    private boolean offersHelp;
    private boolean deleted;

    //Ctor
    public Member() {}


    //toString
    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", needsHelp='" + needsHelp + '\'' +
                ", offersHelp=" + offersHelp +
                '}';
    }

    //Get Set
    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public boolean isNeedsHelp() {
        return needsHelp;
    }
    public void setNeedsHelp(boolean needsHelp) {
        this.needsHelp = needsHelp;
    }
    public boolean isOffersHelp() {
        return offersHelp;
    }
    public void setOffersHelp(boolean offersHelp) {
        this.offersHelp = offersHelp;
    }
    public boolean isDeleted() {
        return deleted;
    }
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
