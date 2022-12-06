package com.SE3_NachhilfeApp.Student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentsRepository extends JpaRepository<Student, Long> {

    //Select * from student where mail = mail
    @Query("Select s from Student s Where s.mail = ?1")
    Optional<Student> findStudentByEmail(String mail);
}
