package com.SE3_NachhilfeApp.Offer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/v1/offer")
public class OfferController {

    private final OfferService offerService;

    @Autowired
    public OfferController(OfferService offerService){
        this.offerService = offerService;
    }

    //GET ALL
    @GetMapping()
    public List<Offer> getAll(){
        return offerService.getAll();
    }

    //GET BY ID
    @GetMapping(path = "byId/{offerId}")
    public Offer getById(@PathVariable("offerId") UUID offerId){
        return offerService.getById(offerId);
    }

    //ADD NEW
    @PostMapping(path = "add")
    public void createNew(@RequestBody Offer offer){
        offerService.addNew(offer);
    }

    //DELETE BY ID
    @DeleteMapping(path = "delete/{offerId}")
    public void deleteById(@PathVariable("offerId") UUID offerId){
        offerService.deleteById(offerId);
    }

    //UPDATE BY ID
    @PutMapping(path = "update/{offerId}")
    public void updateById(@PathVariable("offerId") UUID offerId, @RequestBody UUID subjectId){
        offerService.updateById(offerId, subjectId);
    }
}
