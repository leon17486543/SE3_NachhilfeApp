package com.SE3_NachhilfeApp.Contract;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/v1/contract")
public class ContractController {

    private final ContractService contractService;

    @Autowired
    public ContractController(ContractService contractService){
        this.contractService = contractService;
    }

    //GET ALL
    @GetMapping()
    public List<Contract> getAll(){
        return contractService.getAll();
    }

    //GET BY ID
    @GetMapping(path = "byId/{contractId}")
    public Contract getById(@PathVariable("contractId") UUID contractId){
        return contractService.getById(contractId);
    }

    //ADD NEW
    @PostMapping(path = "add")
    public void createNew(@RequestBody Contract contract){
        contractService.addNew(contract);
    }

    //DELETE BY ID
    @DeleteMapping(path = "delete/{contractId}")
    public void deleteById(@PathVariable("contractId") UUID contractId){
        contractService.deleteById(contractId);
    }

    //UPDATE BY ID
    @PutMapping(path = "update/{contractId}")
    public void updateById(@PathVariable("contractId") UUID contractId, @RequestBody UUID subjectId){
        contractService.updateById(contractId, subjectId);
    }
}
