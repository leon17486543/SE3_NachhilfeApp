package com.SE3_NachhilfeApp.Task;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final String doesNotExistMsg = "Task does not exist";

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    //GET ALL
    public List<Task> getAll(){
        return taskRepository.findAll();
    }

    //GET Task BY ID
    public Task getById(UUID taskID){
        return taskRepository.findById(taskID).orElseThrow(() -> new IllegalStateException(doesNotExistMsg));
    }

    //GET Task BY ASSIGNMENT
    public Optional<List<Task>> getByAssignmentId(UUID assignmentID){
        return taskRepository.findByAssignmentId(assignmentID);
    }

    //ADD NEW Tasks
    public void createNew(Task task) {
        taskRepository.save(task);
    }

    //DELETE Task BY ID
    @Transactional
    public void deleteById(UUID taskID) {
        Task task = taskRepository.findById(taskID).orElseThrow(() -> new IllegalStateException(doesNotExistMsg));
        task.setDeleted(true);
    }

    //DELETE Task BY ASSIGNMENT
    @Transactional
    public void deleteByAssignmentId(UUID assignmentID) {
        List<Task> tasks = taskRepository.findByAssignmentId(assignmentID).orElseThrow(() -> new IllegalStateException(doesNotExistMsg));

        for(Task task : tasks){
            task.setDeleted(true);
        }
    }

    //UPDATE Task BY ID
    @Transactional
    public void updateByID(UUID taskId, String userSolution) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new IllegalStateException(doesNotExistMsg));

        if(userSolution != null && userSolution.length() > 0 && !Objects.equals(task.getCorrectSolution(), userSolution)){
            task.setCorrectSolution(userSolution);
        }

    }
}
