package com.SE3_NachhilfeApp.Subjects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SubjectService {

    private final SubjectRepository subjectRepository;

    @Autowired
    public SubjectService(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    //GET ALL SUBJECTS
    public List<Subject> getSubjects(){
        return subjectRepository.findAll();
    }

    //GET SUBJECTS BY ID
    public Subject getSubjectById(UUID subjectID){
        return subjectRepository.findById(subjectID).orElseThrow(() -> new IllegalStateException("subject does not exist"));

    }

    //ADD NEW SUBJECT
    public void addNewSubject(Subject subject) {
        Optional<Subject> subjectOptional = subjectRepository.findSubjectByName(subject.getName());

        if(subjectOptional.isPresent()){
            throw new IllegalStateException("subject already exists");
        }

        subjectRepository.save(subject);

        System.out.println(subject);
    }

    //DELETE Subject BY ID
    public void deleteSubject(UUID id) {
        subjectRepository.findById(id);
        boolean exists = subjectRepository.existsById(id);

        if(!exists){
            throw new IllegalStateException("subject does not exist");
        }

        subjectRepository.deleteById(id);
    }

    //UPDATE Subject BY ID
    @Transactional
    public void updateSubject(UUID subjectID, String name) {
        Subject subject = subjectRepository.findById(subjectID).orElseThrow(() -> new IllegalStateException("Subject does not exist"));

        if(name != null && name.length()>0){
            subject.setName(name);
        }
    }
}
