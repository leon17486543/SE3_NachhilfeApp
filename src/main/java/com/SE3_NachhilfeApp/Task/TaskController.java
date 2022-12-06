package com.SE3_NachhilfeApp.Task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/v1/tasks")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService){
        this.taskService = taskService;
    }

    @GetMapping()
    public List<Task> getTasks(){
        return taskService.getTasks();
    }

    @PostMapping
    public void createNewTask(@RequestBody Task task){
        taskService.addNewTask(task);
    }

    //TODO DELETE Task
    //DELETE
    /*
    @DeleteMapping(path = "{studentId}")
    public void deleteStudent(@PathVariable("studentId") Long id){
        subjectService.deleteStudent(id);
    }
     */

    //TODO Task is still in JSON format
    //PUT
    @PutMapping(path = "{taskId}")
    public void updateStudent(@PathVariable("taskId") UUID taskid, @RequestBody String userSolution){
        taskService.updateTask(taskid, userSolution);
    }
}
