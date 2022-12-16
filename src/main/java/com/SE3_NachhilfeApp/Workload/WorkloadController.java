package com.SE3_NachhilfeApp.Workload;

import com.SE3_NachhilfeApp.Task.Task;
import com.SE3_NachhilfeApp.Task.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/v1/workload")
public class WorkloadController {

    private final WorkloadService workloadService;

    @Autowired
    public WorkloadController(WorkloadService workloadService){
        this.workloadService = workloadService;
    }

    @GetMapping()
    public List<Workload> getTasks(){
        return workloadService.getWorkload();
    }

    @PostMapping
    public void createNewTask(@RequestBody Workload workload){
        workloadService.addNewWorkload(workload);
    }

    //TODO DELETE Workload
    //DELETE
    /*
    @DeleteMapping(path = "{studentId}")
    public void deleteStudent(@PathVariable("studentId") Long id){
        subjectService.deleteStudent(id);
    }
     */

    //TODO UPDATE Workload
    //PUT
    /*
    @PutMapping(path = "{taskId}")
    public void updateStudent(@PathVariable("taskId") UUID taskid, @RequestBody String userSolution){
        workloadService.updateTask(taskid, userSolution);
    }
    */
}
