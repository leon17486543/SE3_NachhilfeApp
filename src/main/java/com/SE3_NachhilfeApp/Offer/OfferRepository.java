package com.SE3_NachhilfeApp.Offer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OfferRepository extends JpaRepository<Offer, UUID> {

    @Query("Select s from Offer s Where s.memberID = ?1")
    Optional<List<Offer>> findOfferByMember(UUID memberID);

    @Query("Select s from Offer s Where s.subjectID = ?1")
    Optional<List<Offer>> findOfferBySubject(UUID subjectID);

}
