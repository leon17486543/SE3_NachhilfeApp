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

    private final String doesNotExistMsg = "Subject does not exist";

    @Autowired
    public SubjectService(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    //GET ALL
    public List<Subject> getAll(){
        return subjectRepository.findAll();
    }

    //GET Subject BY ID
    public Subject getById(UUID subjectID){
        return subjectRepository.findById(subjectID).orElseThrow(() -> new IllegalStateException(doesNotExistMsg));
    }

    //ADD NEW Subject
    public void createNew(Subject subject) {
        Optional<Subject> subjectOptional = subjectRepository.findSubjectByName(subject.getName());

        if(subjectOptional.isPresent()){
            throw new IllegalStateException("subject already exists");
        }

        subjectRepository.save(subject);

        System.out.println(subject);
    }

    //DELETE Subject BY ID
    @Transactional
    public void deleteById(UUID id) {
        Subject subject = subjectRepository.findById(id).orElseThrow(() -> new IllegalStateException(doesNotExistMsg));

        subject.setDeleted(true);
    }

    //UPDATE Subject BY ID
    @Transactional
    public void updateById(UUID subjectID, String name) {
        Subject subject = subjectRepository.findById(subjectID).orElseThrow(() -> new IllegalStateException(doesNotExistMsg));

        if(name != null && name.length()>0){
            subject.setName(name);
        }
    }
}
