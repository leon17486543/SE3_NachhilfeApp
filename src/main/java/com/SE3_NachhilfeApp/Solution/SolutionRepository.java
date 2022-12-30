package com.SE3_NachhilfeApp.Solution;

import com.SE3_NachhilfeApp.Offer.Offer;
import com.SE3_NachhilfeApp.Submission.Submission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SolutionRepository extends JpaRepository<Solution, UUID> {

    @Query("Select s from Solution s Where s.submissionID = ?1")
    Optional<List<Solution>> findSolutionBySubmission(UUID submissionId);
}
