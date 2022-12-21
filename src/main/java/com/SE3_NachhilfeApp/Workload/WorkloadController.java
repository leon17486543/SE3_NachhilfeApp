package com.SE3_NachhilfeApp.Workload;

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
    public List<Workload> getAll(){
        return workloadService.getAll();
    }

    //GET BY ID
    @GetMapping(path = "byId/{workloadId}")
    public Workload getById(@PathVariable("workloadId") UUID workloadId){
        return workloadService.getById(workloadId);
    }

    //GET BY Schooler
    @GetMapping(path = "bySchooler/{schoolerId}")
    public List<Workload> getBySchooler(@PathVariable("schoolerId") UUID schoolerId){
        return	workloadService.getBySchooler(schoolerId);
    }

    //GET BY Tutor
    @GetMapping(path = "byTutor/{tutorId}")
    public List<Workload> getByTutor(@PathVariable("tutorId") UUID tutorId){
        return	workloadService.getByTutor(tutorId);
    }

    //ADD NEW
    @PostMapping(path="add")
    public void createNew(@RequestBody Workload workload){
        workloadService.createNew(workload);
    }

    //DELETE BY ID
    @DeleteMapping(path = "delete/{workloadId}")
    public void deleteById(@PathVariable("workloadId") UUID workloadId){
        workloadService.deleteById(workloadId);
    }

    //UPDATE BY ID
    @PutMapping(path = "update/{workloadId}")
    public void updateById(@PathVariable("workloadId") UUID workloadId, @RequestParam LocalDate dueDate){
        workloadService.updateById(workloadId, dueDate);
    }
}
