package com.SE3_NachhilfeApp.Solution;


import com.SE3_NachhilfeApp.Submission.Submission;
import com.SE3_NachhilfeApp.Submission.SubmissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class SolutionService {

    private final SolutionRepository solutionRepository;

    @Autowired
    public SolutionService(SolutionRepository solutionRepository) {
        this.solutionRepository = solutionRepository;
    }

    //GET ALL Solutions
    public List<Solution> getSolution(){
        return solutionRepository.findAll();
    }

    //GET Solution BY ID
    public Solution getSolutionById(UUID solutionID){
        return solutionRepository.findById(solutionID).orElseThrow(() -> new IllegalStateException("solution does not exist"));

    }

    //ADD NEW Solution
    public void addNewSolution(Solution solution) {
        solutionRepository.save(solution);
    }

    //DELETE Solution BY ID
    public void deleteSolution(UUID id) {
        solutionRepository.findById(id);
        boolean exists = solutionRepository.existsById(id);

        if(!exists){
            throw new IllegalStateException("solution does not exist");
        }

        solutionRepository.deleteById(id);
    }

    //UPDATE Solution BY ID
    @Transactional
    public void updateSolution(UUID id, String solutionText) {
        Solution solution = solutionRepository.findById(id).orElseThrow(() -> new IllegalStateException("solution does not exist"));

        if(solutionText != null && solutionText.length() > 0){
            solution.setSolutionText(solutionText);
        }

    }


}
