package com.SE3_NachhilfeApp.Contract;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ContractService {

    private final ContractRepository contractRepository;

    @Autowired
    public ContractService(ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }

    //GET ALL
    public List<Contract> getAll(){
        return contractRepository.findAll();
    }

    //GET Contract BY ID
    public Contract getById(UUID contractID){
        return contractRepository.findById(contractID).orElseThrow(() -> new IllegalStateException("Contract does not exist"));

    }

    //ADD NEW Contract
    public void addNew(Contract contract) {
        contractRepository.save(contract);
    }

    //DELETE Contract BY ID
    public void deleteById(UUID contractID) {
        contractRepository.findById(contractID);
        boolean exists = contractRepository.existsById(contractID);

        if(!exists){
            throw new IllegalStateException("Contract does not exist");
        }

        contractRepository.deleteById(contractID);
    }

    //UPDATE Contract BY ID
    @Transactional
    public void updateById(UUID contractID, UUID subjectID) {
        Contract contract = contractRepository.findById(contractID).orElseThrow(() -> new IllegalStateException("Contract does not exist"));

        if(subjectID != null){
            contract.setSubjectID(subjectID);
        }

    }
}
