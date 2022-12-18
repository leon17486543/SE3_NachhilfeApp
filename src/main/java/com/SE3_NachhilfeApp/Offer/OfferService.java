package com.SE3_NachhilfeApp.Offer;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class OfferService {

    private final OfferRepository offerRepository;

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
        return offerRepository.findById(offerID).orElseThrow(() -> new IllegalStateException("Offer does not exist"));

    }

    //ADD NEW Offer
    public void addNew(Offer offer) {
        offerRepository.save(offer);
    }

    //DELETE Offer BY ID
    public void deleteById(UUID offerID) {
        offerRepository.findById(offerID);
        boolean exists = offerRepository.existsById(offerID);

        if(!exists){
            throw new IllegalStateException("Offer does not exist");
        }

        offerRepository.deleteById(offerID);
    }

    //UPDATE Offer BY ID
    @Transactional
    public void updateById(UUID offerID, UUID subjectID) {
        Offer offer = offerRepository.findById(offerID).orElseThrow(() -> new IllegalStateException("Offer does not exist"));

        if(subjectID != null){
            offer.setSubjectID(subjectID);
        }

    }
}
