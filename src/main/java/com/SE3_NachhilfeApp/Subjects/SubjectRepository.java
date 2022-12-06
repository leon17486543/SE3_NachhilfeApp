package com.SE3_NachhilfeApp.Subjects;

import com.SE3_NachhilfeApp.Student.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, UUID> {

    //Select * from subjects where mail = mail
    @Query("Select s from Subject s Where s.name = ?1")
    Optional<Subject> findSubjectByName(String name);

}
