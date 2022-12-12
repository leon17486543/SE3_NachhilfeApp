package com.SE3_NachhilfeApp.Submission;


import com.SE3_NachhilfeApp.Workload.Workload;
import com.SE3_NachhilfeApp.Workload.WorkloadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubmissionService {

    private final SubmissionRepository submissionRepository;

    @Autowired
    public SubmissionService(SubmissionRepository submissionRepository) {
        this.submissionRepository = submissionRepository;
    }

    //GET ALL Tasks
    public List<Submission> getSubmission(){
        return submissionRepository.findAll();
    }

    //ADD NEW Tasks
    public void addNewSubmission(Submission submission) {
        submissionRepository.save(submission);
    }

    //TODO DELETE Submission
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

    //TODO UPDATE Submission
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
