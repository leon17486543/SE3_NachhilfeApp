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

    //GET ALL Tasks
    public List<Task> getTasks(){
        return taskRepository.findAll();
    }

    //GET Task BY ID
    public Task getTaskById(UUID taskID){
        return taskRepository.findById(taskID).orElseThrow(() -> new IllegalStateException("Task does not exist"));
    }

    //ADD NEW Tasks
    public void addNewTask(Task task) {
        taskRepository.save(task);
    }

    //DELETE BY ID
    public void deleteTask(UUID taskID) {
        taskRepository.findById(taskID);
        boolean exists = taskRepository.existsById(taskID);

        if(!exists){
            throw new IllegalStateException("Task does not exist");
        }

        taskRepository.deleteById(taskID);
    }

    //UPDATE BY ID
    @Transactional
    public void updateTask(UUID taskId, String userSolution) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new IllegalStateException("task does not exist"));

        if(userSolution != null && userSolution.length() > 0 && !Objects.equals(task.getCorrectSolution(), userSolution)){
            task.setCorrectSolution(userSolution);
        }

    }
}
