package com.SE3_NachhilfeApp.Solution;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class SolutionService {

    private final SolutionRepository solutionRepository;

    private final String doesNotExistMsg = "Solution does not exist";

    @Autowired
    public SolutionService(SolutionRepository solutionRepository) {
        this.solutionRepository = solutionRepository;
    }

    //GET ALL
    public List<Solution> getAll(){
        return solutionRepository.findAll();
    }

    //GET Solution BY ID
    public Solution getById(UUID solutionID){
        return solutionRepository.findById(solutionID).orElseThrow(() -> new IllegalStateException(doesNotExistMsg));
    }

    //ADD NEW Solution
    public void createNew(Solution solution) {
        solutionRepository.save(solution);
    }

    //DELETE Solution BY ID
    @Transactional
    public void deleteById(UUID id) {
        Solution solution = solutionRepository.findById(id).orElseThrow(() -> new IllegalStateException(doesNotExistMsg));

        solution.setDeleted(true);
    }

    //DELETE Solution BY SUBMISSION
    @Transactional
    public void deleteBySubmissionId(UUID submissionId) {
        List<Solution> solutions = solutionRepository.findSolutionBySubmission(submissionId).orElseThrow(() -> new IllegalStateException(doesNotExistMsg));

        for(Solution s : solutions){
            s.setDeleted(true);
        }
    }

    //UPDATE Solution BY ID
    @Transactional
    public void updateById(UUID id, String solutionText) {
        Solution solution = solutionRepository.findById(id).orElseThrow(() -> new IllegalStateException(doesNotExistMsg));

        if(solutionText != null && solutionText.length() > 0){
            solution.setSolutionText(solutionText);
        }

    }


}
