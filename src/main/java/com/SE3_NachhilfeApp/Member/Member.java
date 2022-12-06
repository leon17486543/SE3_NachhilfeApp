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

    public Member() {
    }

    public Member(String name, boolean needsHelp, boolean offersHelp) {
        this.name = name;
        this.needsHelp = needsHelp;
        this.offersHelp = offersHelp;
    }

    //TODO set needs / offers help

    public String getName(){return this.name;}

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", needsHelp='" + needsHelp + '\'' +
                ", offersHelp=" + offersHelp +
                '}';
    }
}
