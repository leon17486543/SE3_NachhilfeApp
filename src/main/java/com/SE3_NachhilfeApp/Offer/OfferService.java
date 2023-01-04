package com.SE3_NachhilfeApp.Offer;


import com.SE3_NachhilfeApp.Contract.Contract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class OfferService {

    private final OfferRepository offerRepository;
    private final String doesNotExistMsg = "Offer does not exist";
    private final String hasNoOffersMsg = "User has no offers";

    @Autowired
    public OfferService(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }

    //GET ALL
    public List<Offer> getAll(){
        return offerRepository.findAll();
    }

    //GET Offer BY ID
    public Offer getById(UUID offerID){
        return offerRepository.findById(offerID).orElseThrow(() -> new IllegalStateException(doesNotExistMsg));
    }

    //GET Offer BY MEMBER
    public List<Offer> getByMember(UUID memberID){
        return offerRepository.findOfferByMember(memberID).orElseThrow(() -> new IllegalStateException(hasNoOffersMsg));
    }

    //GET Offer BY Subject
    public List<Offer> getBySubject(UUID subjectID){
        return offerRepository.findOfferBySubject(subjectID).orElseThrow(() -> new IllegalStateException(hasNoOffersMsg));
    }

    //ADD NEW Offer
    public void addNew(Offer offer) {
        offerRepository.save(offer);
    }

    //DELETE Offer BY ID
    @Transactional
    public void deleteById(UUID offerID) {
        Offer offer = offerRepository.findById(offerID).orElseThrow(() -> new IllegalStateException(doesNotExistMsg));
        offer.setDeleted(true);
    }

    //DELETE Offer BY Member
    @Transactional
    public void deleteByMember(UUID memberID) {
        List<Offer> offers = getByMember(memberID);

        for(Offer o : offers){
            o.setDeleted(true);
        }
    }

    //UPDATE Offer BY ID
    @Transactional
    public void updateById(UUID offerID, UUID subjectID) {
        Offer offer = offerRepository.findById(offerID).orElseThrow(() -> new IllegalStateException(doesNotExistMsg));

        if(subjectID != null){
            offer.setSubjectID(subjectID);
        }

    }
}
