package com.SE3_NachhilfeApp.Solution;

import com.SE3_NachhilfeApp.Submission.Submission;
import com.SE3_NachhilfeApp.Submission.SubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/solution")
public class SolutionController {

    private final SolutionService solutionService;

    @Autowired
    public SolutionController(SolutionService solutionService){
        this.solutionService = solutionService;
    }

    @GetMapping()
    public List<Solution> getTasks(){
        return solutionService.getSolution();
    }

    @PostMapping
    public void createNewTask(@RequestBody Solution solution){
        solutionService.addNewSolution(solution);
    }

    //TODO DELETE solution
    //DELETE
    /*
    @DeleteMapping(path = "{studentId}")
    public void deleteStudent(@PathVariable("studentId") Long id){
        subjectService.deleteStudent(id);
    }
     */

    //TODO UPDATE Solution
    //PUT
    /*
    @PutMapping(path = "{taskId}")
    public void updateStudent(@PathVariable("taskId") UUID taskid, @RequestBody String userSolution){
        workloadService.updateTask(taskid, userSolution);
    }
    */
}
