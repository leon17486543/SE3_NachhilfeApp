package com.SE3_NachhilfeApp.Subjects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/v1/subjects")
public class SubjectController {

    private final SubjectService subjectService;

    @Autowired
    public SubjectController(SubjectService subjectService){
        this.subjectService = subjectService;
    }

    //GET BY ID
    @GetMapping()
    public List<Subject> getSubjects(){
        return	subjectService.getSubjects();
    }

    //GET BY ID
    @GetMapping(path = "byId/{subjectId}")
    public Subject getSubjectById(@PathVariable("subjectId") UUID subjectId){
        return subjectService.getSubjectById(subjectId);
    }

    //ADD NEW
    @PostMapping
    public void createNewSubject(@RequestBody Subject subject){
        subjectService.addNewSubject(subject);
    }

    //DELETE BY ID
    @DeleteMapping(path = "delete/{subjectId}")
    public void deleteStudent(@PathVariable("subjectId") UUID subjectId){
        subjectService.deleteSubject(subjectId);
    }

    //PUT
    @PutMapping(path = "update/{subjectId}")
    public void updateStudent(@PathVariable("subjectId") UUID subjectId,
                              @RequestParam() String name){
        subjectService.updateSubject(subjectId, name);
    }
}
