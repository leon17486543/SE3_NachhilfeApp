package com.SE3_NachhilfeApp.Task;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

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
        return taskRepository.findById(taskID).orElseThrow(() -> new IllegalStateException("Task does not exist"));
    }

    //ADD NEW Tasks
    public void createNew(Task task) {
        taskRepository.save(task);
    }

    //DELETE Task BY ID
    public void deleteById(UUID taskID) {
        taskRepository.findById(taskID);
        boolean exists = taskRepository.existsById(taskID);

        if(!exists){
            throw new IllegalStateException("Task does not exist");
        }

        taskRepository.deleteById(taskID);
    }

    //UPDATE Task BY ID
    @Transactional
    public void updateByID(UUID taskId, String userSolution) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new IllegalStateException("task does not exist"));

        if(userSolution != null && userSolution.length() > 0 && !Objects.equals(task.getCorrectSolution(), userSolution)){
            task.setCorrectSolution(userSolution);
        }

    }
}
