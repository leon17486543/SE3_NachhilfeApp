package com.SE3_NachhilfeApp.Submission;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/v1/submission")
public class SubmissionController {

    private final SubmissionService submissionService;

    @Autowired
    public SubmissionController(SubmissionService submissionService){
        this.submissionService = submissionService;
    }

    //ALL
    @GetMapping()
    public List<Submission> getSubmission(){
        return submissionService.getSubmission();
    }

    //GET BY ID
    @GetMapping(path = "byId/{submissionId}")
    public Submission getSubmissionById(@PathVariable("submissionId") UUID submissionId){
        return submissionService.getSubmissionById(submissionId);
    }

    //ADD NEW
    @PostMapping
    public void createNewSubmission(@RequestBody Submission submission){
        submissionService.addNewSubmission(submission);
    }


    //DELETE BY ID
    @DeleteMapping(path = "{delete/{submissionId}")
    public void deleteSubmission(@PathVariable("submissionId") UUID submissionId){
        submissionService.deleteSubmission(submissionId);
    }


    //UPDATE BY ID
    @PutMapping(path = "update/{submissionId}")
    public void updateStudent(@PathVariable("submissionId") UUID submissionId, @RequestBody LocalDate date){
        submissionService.updateSubmission(submissionId, date);
    }
}
