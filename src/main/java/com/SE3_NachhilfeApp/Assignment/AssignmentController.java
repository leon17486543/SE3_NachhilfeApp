package com.SE3_NachhilfeApp.Assignment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/v1/assignments")
public class AssignmentController {

    private final AssignmentService assignmentService;

    @Autowired
    public AssignmentController(AssignmentService assignmentService){
        this.assignmentService = assignmentService;
    }

    //GET ALL
    @GetMapping()
    public List<Assignment> getAll(){
        return	assignmentService.getAll();
    }

    //GET BY ID
    @GetMapping(path = "byId/{assignmentId}")
    public Assignment getById(@PathVariable("assignmentId") UUID assignmentId){
        return	assignmentService.getById(assignmentId);
    }

    //GET BY OWNER
    @GetMapping(path = "byOwner/{ownerId}")
    public List<Assignment> getByOwner(@PathVariable("ownerId") UUID ownerId){
        return	assignmentService.getByOwner(ownerId);
    }

    //ADD NEW
    @PostMapping(path = "add")
    public void createNew(@RequestBody Assignment assignment){
        assignmentService.addNew(assignment);
    }

    //DELETE BY ID
    @DeleteMapping(path = "deleteById/{assignmentId}")
    public void deleteById(@PathVariable("assignmentId") UUID assignmentId){
        assignmentService.deleteById(assignmentId);
    }

    //DELETE BY OWNER
    @DeleteMapping(path = "deleteByOwner/{ownerId}")
    public void deleteByOwner(@PathVariable("ownerId") UUID ownerId){
        assignmentService.deleteByOwner(ownerId);
    }

    //UPDATE BY ID
    @PutMapping(path = "update/{assignmentId}")
    public void updateById(@PathVariable("assignmentId") UUID assignmentId,
                              @RequestParam(required = false) String name,
                              @RequestParam(required = false) String description,
                                 @RequestParam(required = false) UUID subjectId){
        assignmentService.updateById(assignmentId, name, description, subjectId);
    }

}
