package com.SE3_NachhilfeApp.Solution;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/v1/solution")
public class SolutionController {

    private final SolutionService solutionService;

    @Autowired
    public SolutionController(SolutionService solutionService){
        this.solutionService = solutionService;
    }

    //ALL
    @GetMapping()
    public List<Solution> getAll(){
        return solutionService.getAll();
    }

    //GET BY ID
    @GetMapping(path = "byId/{solutionId}")
    public Solution getById(@PathVariable("solutionId") UUID solutionId){
        return solutionService.getById(solutionId);
    }

    //ADD NEW
    @PostMapping(path = "add")
    public void createNew(@RequestBody Solution solution){
        solutionService.createNew(solution);
    }

    //DELETE BY ID
    @DeleteMapping(path = "delete/{solutionId}")
    public void deleteById(@PathVariable("solutionId") UUID solutionId){
        solutionService.deleteById(solutionId);
    }

    //UPDATE BY ID
    @PutMapping(path = "update/{solutionId}")
    public void updateById(@PathVariable("solutionId") UUID solutionId,
                             @RequestParam() String solutionText){
        solutionService.updateById(solutionId, solutionText);
    }
}
