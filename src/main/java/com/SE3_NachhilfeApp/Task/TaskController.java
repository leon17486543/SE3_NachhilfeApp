package com.SE3_NachhilfeApp.Task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/v1/tasks")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService){
        this.taskService = taskService;
    }

    //ALL
    @GetMapping()
    public List<Task> getTasks(){
        return taskService.getTasks();
    }

    //GET BY ID
    @GetMapping(path = "byId/{taskId}")
    public Task getTaskById(@PathVariable("taskId") UUID taskId){
        return taskService.getTaskById(taskId);
    }

    //ADD NEW
    @PostMapping
    public void createNewTask(@RequestBody Task task){
        taskService.addNewTask(task);
    }

    //DELETE BY ID
    @DeleteMapping(path = "delete/{taskId}")
    public void deleteTask(@PathVariable("taskId") UUID taskId){
        taskService.deleteTask(taskId);
    }

    //PUT
    @PutMapping(path = "update/{taskId}")
    public void updateStudent(@PathVariable("taskId") UUID taskid, @RequestBody String userSolution){
        taskService.updateTask(taskid, userSolution);
    }
}
