package com.SE3_NachhilfeApp.Solution;


import com.SE3_NachhilfeApp.Submission.Submission;
import com.SE3_NachhilfeApp.Submission.SubmissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SolutionService {

    private final SolutionRepository solutionRepository;

    @Autowired
    public SolutionService(SolutionRepository solutionRepository) {
        this.solutionRepository = solutionRepository;
    }

    //GET ALL Tasks
    public List<Solution> getSolution(){
        return solutionRepository.findAll();
    }

    //ADD NEW Tasks
    public void addNewSolution(Solution solution) {
        solutionRepository.save(solution);
    }

    //TODO DELETE Solution
    /*
    public void deleteStudent(Long id) {
        subjectRepository.findById(id);
        boolean exists = subjectRepository.existsById(id);

        if(!exists){
            throw new IllegalStateException("student does not exist");
        }

        subjectRepository.deleteById(id);
    }
    */

    //TODO UPDATE Solution
    /*
    @Transactional
    public void updateTask(UUID taskId, String userSolution) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new IllegalStateException("task does not exist"));

        if(userSolution != null && userSolution.length() > 0 && !Objects.equals(task.getUserSolution(), userSolution)){
            task.setUserSolution(userSolution);
        }

    }

     */
}
