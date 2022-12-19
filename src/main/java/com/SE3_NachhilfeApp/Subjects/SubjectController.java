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

    //All
    @GetMapping()
    public List<Subject> getAll(){
        return	subjectService.getAll();
    }

    //GET BY ID
    @GetMapping(path = "byId/{subjectId}")
    public Subject getById(@PathVariable("subjectId") UUID subjectId){
        return subjectService.getById(subjectId);
    }

    //ADD NEW
    @PostMapping(path = "add")
    public void createNew(@RequestBody Subject subject){
        subjectService.createNew(subject);
    }

    //DELETE BY ID
    @DeleteMapping(path = "delete/{subjectId}")
    public void deleteById(@PathVariable("subjectId") UUID subjectId){
        subjectService.deleteById(subjectId);
    }

    //UPDATE BY ID
    @PutMapping(path = "update/{subjectId}")
    public void updateById(@PathVariable("subjectId") UUID subjectId,
                              @RequestParam() String name){
        subjectService.updateById(subjectId, name);
    }
}
