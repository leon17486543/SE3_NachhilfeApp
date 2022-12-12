package com.SE3_NachhilfeApp.Subjects;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table
public class Subject {
    @Id
    private UUID id = UUID.randomUUID();
    private String name;

    //Ctor
    public Subject() {

    }
    public Subject(String name) {
        this.name = name;
    }

    //toString
    @Override
    public String toString() {
        return "Subjects{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    //Get Set
    public UUID getId() {
        return id;
    }
    public String getName() {
        return name;
    }
}
