package com.SE3_NachhilfeApp.Contract;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ContractService {

    private final ContractRepository contractRepository;
    private final String doesNotExistMsg = "Contract does not exist";
    private final String doesNotExistMsg_ByPerson = "User has no contracts";

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
        return contractRepository.findById(contractID).orElseThrow(() -> new IllegalStateException(doesNotExistMsg));
    }

    //GET Contract BY TUTOR
    public List<Contract> getByTutor(UUID id){
        return contractRepository.findContractByTutor(id).orElseThrow(() -> new IllegalStateException(doesNotExistMsg_ByPerson));
    }

    //GET Contract BY SCHOOLER
    public List<Contract> getBySchooler(UUID id){
        return contractRepository.findContractBySchooler(id).orElseThrow(() -> new IllegalStateException(doesNotExistMsg_ByPerson));
    }

    //ADD NEW Contract
    public void addNew(Contract contract) {
        contractRepository.save(contract);
    }

    //DELETE Contract BY ID
    @Transactional
    public void deleteById(UUID contractID) {
        Contract contract = contractRepository.findById(contractID).orElseThrow(() -> new IllegalStateException(doesNotExistMsg));
        contract.setDeleted(true);
    }

    //UPDATE Contract BY ID
    @Transactional
    public void updateById(UUID contractID, UUID subjectID) {
        Contract contract = contractRepository.findById(contractID).orElseThrow(() -> new IllegalStateException(doesNotExistMsg));

        if(subjectID != null){
            contract.setSubjectID(subjectID);
        }

    }
}
