package com.SE3_NachhilfeApp.Task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
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

    //GET BY AssignmentId
    @GetMapping(path = "byAssignmentId/{assignmentId}")
    public Optional<List<Task>> getByAssignmentId(@PathVariable("assignmentId") UUID assignmentId){
        return taskService.getByAssignmentId(assignmentId);
    }

    //ADD NEW
    @PostMapping(path = "add")
    public void createNew(@RequestBody Task task){
        taskService.createNew(task);
    }

    //DELETE BY ID
    @DeleteMapping(path = "deleteById/{taskId}")
    public void deleteById(@PathVariable("taskId") UUID taskId){
        taskService.deleteById(taskId);
    }

    //DELETE BY AssignmentID
    @DeleteMapping(path = "deleteByAssignmentId/{assignmentId}")
    public void deleteByAssignmentId(@PathVariable("assignmentId") UUID assignmentId){
        taskService.deleteByAssignmentId(assignmentId);
    }

    //UPDATE Task BY ID
    @PutMapping(path = "update/{taskId}")
    public void updateByID(@PathVariable("taskId") UUID taskid, @RequestParam(required = false) String name, @RequestParam(required = false) String correctSolution){
        taskService.updateByID(taskid, name, correctSolution);
    }
}
