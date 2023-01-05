package com.SE3_NachhilfeApp.Submission;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/v1/submission")
public class SubmissionController {

    private final SubmissionService submissionService;

    @Autowired
    public SubmissionController(SubmissionService submissionService){
        this.submissionService = submissionService;
    }

    //GET ALL
    @GetMapping()
    public List<Submission> getAll(){
        return submissionService.getAll();
    }

    //GET BY ID
    @GetMapping(path = "byId/{submissionId}")
    public Submission getById(@PathVariable("submissionId") UUID submissionId){
        return submissionService.getById(submissionId);
    }

    //ADD NEW
    @PostMapping(path = "add")
    public void createNew(@RequestBody Submission submission){
        submissionService.createNew(submission);
    }


    //DELETE BY ID
    @DeleteMapping(path = "delete/{submissionId}")
    public void deleteById(@PathVariable("submissionId") UUID submissionId){
        submissionService.deleteById(submissionId);
    }

    //UPDATE BY ID
    @PutMapping(path = "update/{submissionId}")
    public void updateById(@PathVariable("submissionId") UUID submissionId, @RequestParam LocalDate submissionDate){
        submissionService.updateById(submissionId, submissionDate);
    }
}
