package com.SE3_NachhilfeApp.Subjects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/subjects")
public class SubjectController {

    private final SubjectService subjectService;

    @Autowired
    public SubjectController(SubjectService subjectService){
        this.subjectService = subjectService;
    }

    @GetMapping()
    public List<Subject> getSubjects(){
        return	subjectService.getSubjects();
    }

    @PostMapping
    public void createNewSubject(@RequestBody Subject subject){
        subjectService.addNewSubject(subject);
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
