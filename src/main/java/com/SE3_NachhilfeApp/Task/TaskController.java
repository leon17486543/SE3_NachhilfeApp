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
    public List<Task> getAll(){
        return taskService.getAll();
    }

    //GET BY ID
    @GetMapping(path = "byId/{taskId}")
    public Task getById(@PathVariable("taskId") UUID taskId){
        return taskService.getById(taskId);
    }

    //ADD NEW
    @PostMapping
    public void createNew(@RequestBody Task task){
        taskService.createNew(task);
    }

    //DELETE BY ID
    @DeleteMapping(path = "delete/{taskId}")
    public void deleteById(@PathVariable("taskId") UUID taskId){
        taskService.deleteById(taskId);
    }

    //PUT
    @PutMapping(path = "update/{taskId}")
    public void updateByID(@PathVariable("taskId") UUID taskid, @RequestBody String userSolution){
        taskService.updateByID(taskid, userSolution);
    }
}
