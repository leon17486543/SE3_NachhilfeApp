package com.SE3_NachhilfeApp.Assignment;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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


    @PostMapping
    public void createNewAssignment(@RequestBody Assignment assignment){
        assignmentService.addNewAssignment(assignment);
    }

    //TODO DELETE SUBJECT
    //DELETE
    /*
    @DeleteMapping(path = "{studentId}")
    public void deleteStudent(@PathVariable("studentId") Long id){
        subjectService.deleteStudent(id);
    }
     */

    //TODO UPDATE SUBJECT
    //PUT
    /*
    @PutMapping(path = "{studentId}")
    public void updateStudent(@PathVariable("studentId") Long studentId,
                              @RequestParam("required = false") String name,
                              @RequestParam("required = false") String mail){
        subjectService.updateStudent(studentId, name, mail);
    }

     */
}
