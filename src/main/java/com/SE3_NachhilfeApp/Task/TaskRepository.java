package com.SE3_NachhilfeApp.Task;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {

    @Query("SELECT s FROM Task s WHERE s.assignmentID = ?1")
    Optional<List<Task>> findByAssignmentId(UUID assignmentID);
}
