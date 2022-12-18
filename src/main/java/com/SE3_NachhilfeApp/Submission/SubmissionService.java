package com.SE3_NachhilfeApp.Submission;


import com.SE3_NachhilfeApp.Solution.SolutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class SubmissionService {

    private final SubmissionRepository submissionRepository;
    private final SolutionService solutionService;

    private final String doesNotExistMsg = "Submission does not exist";

    @Autowired
    public SubmissionService(SubmissionRepository submissionRepository, SolutionService solutionService) {
        this.submissionRepository = submissionRepository;
        this.solutionService = solutionService;
    }

    //GET ALL
    public List<Submission> getAll(){
        return submissionRepository.findAll();
    }

    //GET Submission BY ID
    public Submission getById(UUID submissionID){
        return submissionRepository.findById(submissionID).orElseThrow(() -> new IllegalStateException(doesNotExistMsg));
    }

    //ADD NEW Submission
    public void createNew(Submission submission) {
        submissionRepository.save(submission);
    }

    //DELETE Submission BY ID
    @Transactional
    public void deleteById(UUID submissionID) {
        Submission submission = submissionRepository.findById(submissionID).orElseThrow(() -> new IllegalStateException(doesNotExistMsg));

        submission.setDeleted(true);

        solutionService.deleteBySubmissionId(submissionID);
    }

    //UPDATE Submission BY ID
    @Transactional
    public void updateById(UUID submissionID, LocalDate date) {
        Submission submission = submissionRepository.findById(submissionID).orElseThrow(() -> new IllegalStateException(doesNotExistMsg));

        if(date != null){
            submission.setSubmissionDate(date);
        }
    }
}
