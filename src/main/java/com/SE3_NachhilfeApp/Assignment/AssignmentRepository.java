package com.SE3_NachhilfeApp.Assignment;

import com.SE3_NachhilfeApp.Subjects.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, UUID> {

    @Query("Select s from Assignment s Where s.name = ?1")
    Optional<Assignment> findAssignmentByName(String name);

}
