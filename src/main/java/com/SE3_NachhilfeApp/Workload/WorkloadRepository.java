package com.SE3_NachhilfeApp.Workload;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface WorkloadRepository extends JpaRepository<Workload, UUID> {

    @Query("Select s from Workload s Where s.tutorID = ?1")
    Optional<List<Workload>> findWorkloadByTutor(UUID tutorId);

    @Query("Select s from Workload s Where s.schoolerID = ?1")
    Optional<List<Workload>> findWorkloadBySchooler(UUID schoolerId);
}
