package com.SE3_NachhilfeApp.Workload;


import com.SE3_NachhilfeApp.Task.Task;
import com.SE3_NachhilfeApp.Task.TaskRepository;
import org.hibernate.jdbc.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class WorkloadService {

    private final WorkloadRepository workloadRepository;

    @Autowired
    public WorkloadService(WorkloadRepository workloadRepository) {
        this.workloadRepository = workloadRepository;
    }

    //GET ALL Tasks
    public List<Workload> getWorkload(){
        return workloadRepository.findAll();
    }

    //ADD NEW Tasks
    public void addNewWorkload(Workload workload) {
        workloadRepository.save(workload);
    }

    //TODO DELETE Workload
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

    //TODO UPDATE Workload
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
