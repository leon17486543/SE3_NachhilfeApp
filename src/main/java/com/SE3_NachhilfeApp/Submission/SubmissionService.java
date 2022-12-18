package com.SE3_NachhilfeApp.Submission;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class SubmissionService {

    private final SubmissionRepository submissionRepository;

    @Autowired
    public SubmissionService(SubmissionRepository submissionRepository) {
        this.submissionRepository = submissionRepository;
    }

    //GET ALL
    public List<Submission> getAll(){
        return submissionRepository.findAll();
    }

    //GET Submission BY ID
    public Submission getById(UUID submissionID){
        return submissionRepository.findById(submissionID).orElseThrow(() -> new IllegalStateException("Submission does not exist"));
    }

    //ADD NEW Submission
    public void createNew(Submission submission) {
        submissionRepository.save(submission);
    }

    //DELETE Submission BY ID
    public void deleteById(UUID submissionID) {
        submissionRepository.findById(submissionID);
        boolean exists = submissionRepository.existsById(submissionID);

        if(!exists){
            throw new IllegalStateException("Submission does not exist");
        }

        submissionRepository.deleteById(submissionID);
    }

    //UPDATE Submission BY ID
    @Transactional
    public void updateById(UUID submissionID, LocalDate date) {
        Submission submission = submissionRepository.findById(submissionID).orElseThrow(() -> new IllegalStateException("submission does not exist"));

        if(date != null){
            submission.setSubmissionDate(date);
        }

    }
}
