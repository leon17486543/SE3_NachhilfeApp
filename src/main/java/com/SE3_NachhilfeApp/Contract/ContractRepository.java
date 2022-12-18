package com.SE3_NachhilfeApp.Contract;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ContractRepository extends JpaRepository<Contract, UUID> {

    @Query("Select s from Contract s Where s.toutorID = ?1 AND s.deleted = false")
    Optional<List<Contract>> findContractByTutor(UUID toutorID);

    @Query("Select s from Contract s Where s.schoolerID = ?1 AND s.deleted = false")
    Optional<List<Contract>> findContractBySchooler(UUID schoolerID);
}
