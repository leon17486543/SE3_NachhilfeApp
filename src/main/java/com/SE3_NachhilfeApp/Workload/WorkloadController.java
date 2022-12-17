package com.SE3_NachhilfeApp.Workload;

import com.SE3_NachhilfeApp.Task.Task;
import com.SE3_NachhilfeApp.Task.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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

    //GET ALL
    @GetMapping()
    public List<Workload> getTasks(){
        return workloadService.getWorkload();
    }

    //GET BY ID
    @GetMapping(path = "byId/{workloadId}")
    public Workload getWorkloadById(@PathVariable("workloadId") UUID workloadId){
        return workloadService.getWorkloadById(workloadId);
    }

    //ADD NEW
    @PostMapping
    public void createNewTask(@RequestBody Workload workload){
        workloadService.addNewWorkload(workload);
    }

    //DELETE BY ID
    @DeleteMapping(path = "delete/{workloadId}")
    public void deleteWorkload(@PathVariable("workloadId") UUID workloadId){
        workloadService.deleteWorkload(workloadId);
    }

    //UPDATE BY ID
    @PutMapping(path = "update/{workloadId}")
    public void updateStudent(@PathVariable("workloadId") UUID workloadId, @RequestBody LocalDate dueDate){
        workloadService.updateWorkload(workloadId, dueDate);
    }
}
