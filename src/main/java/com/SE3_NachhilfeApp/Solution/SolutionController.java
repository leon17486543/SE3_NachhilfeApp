package com.SE3_NachhilfeApp.Solution;

import com.SE3_NachhilfeApp.Member.Member;
import com.SE3_NachhilfeApp.Submission.Submission;
import com.SE3_NachhilfeApp.Submission.SubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/v1/solution")
public class SolutionController {

    private final SolutionService solutionService;

    @Autowired
    public SolutionController(SolutionService solutionService){
        this.solutionService = solutionService;
    }

    //ALL
    @GetMapping()
    public List<Solution> getSolution(){
        return solutionService.getSolution();
    }

    //GET BY ID
    @GetMapping(path = "byId/{solutionId}")
    public Solution getSolutionById(@PathVariable("solutionId") UUID solutionId){
        return solutionService.getSolutionById(solutionId);
    }

    //ADD NEW
    @PostMapping(path = "add")
    public void createNewTask(@RequestBody Solution solution){
        solutionService.addNewSolution(solution);
    }

    //DELETE BY ID
    @DeleteMapping(path = "delete/{solutionId}")
    public void deleteSolution(@PathVariable("solutionId") UUID solutionId){
        solutionService.deleteSolution(solutionId);
    }

    //UPDATE BY ID
    @PutMapping(path = "update/{solutionId}")
    public void updateSolution(@PathVariable("solutionId") UUID solutionId,
                             @RequestParam() String solutionText){
        solutionService.updateSolution(solutionId, solutionText);
    }
}
