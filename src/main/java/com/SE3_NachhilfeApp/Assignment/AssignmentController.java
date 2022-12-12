package com.SE3_NachhilfeApp.Assignment;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/v1/assignments")
public class AssignmentController {

    private final AssignmentService assignmentService;

    @Autowired
    public AssignmentController(AssignmentService assignmentService){
        this.assignmentService = assignmentService;
    }

    @GetMapping()
    public List<Assignment> getAssignments(){
        return	assignmentService.getAssignments();
    }

    @GetMapping(path = "byId/{assignmentId}")
    public Assignment getAssignmentsById(@PathVariable("assignmentId") UUID assignmentId){
        return	assignmentService.getAssignmentsById(assignmentId);
    }

    @GetMapping(path = "byOwner/{ownerId}")
    public List<Assignment> getAssignmentsByOwner(@PathVariable("ownerId") UUID ownerId){
        return	assignmentService.getAssignmentsByOwner(ownerId);
    }


    @PostMapping(path = "add")
    public void createNewAssignment(@RequestBody Assignment assignment){
        assignmentService.addNewAssignment(assignment);
    }

    //DELETE
    @DeleteMapping(path = "delete/{assignmentId}")
    public void deleteAssignment(@PathVariable("assignmentId") UUID assignmentId){
        assignmentService.deleteAssignment(assignmentId);
    }


    //PUT
    @PutMapping(path = "update/{assignmentId}")
    public void updateAssignment(@PathVariable("assignmentId") UUID assignmentId,
                              @RequestParam(required = false) String name,
                              @RequestParam(required = false) String description,
                                 @RequestParam(required = false) UUID subjectId){
        assignmentService.updateAssignment(assignmentId, name, description, subjectId);
    }

}
