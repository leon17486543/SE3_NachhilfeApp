package com.SE3_NachhilfeApp.Submission;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/submission")
public class SubmissionController {

    private final SubmissionService submissionService;

    @Autowired
    public SubmissionController(SubmissionService submissionService){
        this.submissionService = submissionService;
    }

    @GetMapping()
    public List<Submission> getTasks(){
        return submissionService.getSubmission();
    }

    @PostMapping
    public void createNewTask(@RequestBody Submission submission){
        submissionService.addNewSubmission(submission);
    }

    //TODO DELETE Submission
    //DELETE
    /*
    @DeleteMapping(path = "{studentId}")
    public void deleteStudent(@PathVariable("studentId") Long id){
        subjectService.deleteStudent(id);
    }
     */

    //TODO UPDATE Submission
    //PUT
    /*
    @PutMapping(path = "{taskId}")
    public void updateStudent(@PathVariable("taskId") UUID taskid, @RequestBody String userSolution){
        workloadService.updateTask(taskid, userSolution);
    }
    */
}
