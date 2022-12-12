package com.SE3_NachhilfeApp.Solution;

import com.SE3_NachhilfeApp.Submission.Submission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SolutionRepository extends JpaRepository<Solution, UUID> {

}
