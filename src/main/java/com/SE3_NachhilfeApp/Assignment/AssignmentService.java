package com.SE3_NachhilfeApp.Assignment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;
import java.util.List;
import java.util.Optional;

@Service
public class AssignmentService {

    private final AssignmentRepository assignmentRepository;

    @Autowired
    public AssignmentService(AssignmentRepository assignmentRepository) {
        this.assignmentRepository = assignmentRepository;
    }

    //GET ALL ASSIGNMENT
    public List<Assignment> getAssignments(){
        return assignmentRepository.findAll();
    }

    //GET ALL ASSIGNMENT
    public Assignment getAssignmentsById(UUID id){
        return assignmentRepository.findById(id).orElseThrow(() -> new IllegalStateException("assignment does not exist"));
    }

    public List<Assignment> getAssignmentsByOwner(UUID id){
        return assignmentRepository.findAssignmentByOwner(id).orElseThrow(() -> new IllegalStateException("owner does not have assignments"));
    }


    //ADD NEW ASSIGNMENT
    public void addNewAssignment(Assignment assignment) {
        assignmentRepository.save(assignment);
    }

    //DELETE ASSIGNMENT
    public void deleteAssignment(UUID id) {
        assignmentRepository.findById(id);
        boolean exists = assignmentRepository.existsById(id);

        if(!exists){
            throw new IllegalStateException("assignment does not exist");
        }

        assignmentRepository.deleteById(id);
    }

    //UPDATE ASSIGNMENT
    @Transactional
    public void updateAssignment(UUID assignmentId, String name, String description, UUID subjectID) {
        Assignment assignment = assignmentRepository.findById(assignmentId).orElseThrow(() -> new IllegalStateException("assignment does not exist"));

        if(name != null && name.length() > 0 && !Objects.equals(assignment.getName(), name)){
            assignment.setName(name);
        }

        if(description != null && description.length() > 0 && !Objects.equals(assignment.getDescription(), description)){
            assignment.setDescription(description);
        }

        if(subjectID != null && !Objects.equals(assignment.getSubject(), subjectID)){
            assignment.setSubject(subjectID);
        }

    }
}
