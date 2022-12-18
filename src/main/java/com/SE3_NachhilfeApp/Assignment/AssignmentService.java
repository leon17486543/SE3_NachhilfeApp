package com.SE3_NachhilfeApp.Assignment;

import com.SE3_NachhilfeApp.Task.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;
import java.util.List;

@Service
public class AssignmentService {

    private final AssignmentRepository assignmentRepository;
    private final TaskService taskService;

    //Error Messages
    private final String doesNotExistMsg = "Assignment does not exist";
    private final String doesNotExistMsg_ByOwner = "Owner does not have Assignments";

    @Autowired
    public AssignmentService(AssignmentRepository assignmentRepository, TaskService taskService) {
        this.assignmentRepository = assignmentRepository;
        this.taskService = taskService;
    }

    //GET ALL
    public List<Assignment> getAll(){
        return assignmentRepository.findAll();
    }

    //GET Assignment BY ID
    public Assignment getById(UUID id){
        return assignmentRepository.findById(id).orElseThrow(() -> new IllegalStateException(doesNotExistMsg));
    }

    //GET Assignment BY OWNER
    public List<Assignment> getByOwner(UUID id){
        return assignmentRepository.findAssignmentByOwner(id).orElseThrow(() -> new IllegalStateException(doesNotExistMsg_ByOwner));
    }

    //ADD NEW Assignment
    public void addNew(Assignment assignment) {
        assignmentRepository.save(assignment);
    }

    //DELETE Assignment BY ID
    @Transactional
    public void deleteById(UUID id) {
        Assignment assignment = assignmentRepository.findById(id).orElseThrow(() -> new IllegalStateException(doesNotExistMsg));
        assignment.setDeleted(true);

        taskService.deleteByAssignmentId(id);
    }

    //UPDATE Assignment BY ID
    @Transactional
    public void updateById(UUID assignmentId, String name, String description, UUID subjectID) {
        Assignment assignment = assignmentRepository.findById(assignmentId).orElseThrow(() -> new IllegalStateException(doesNotExistMsg));

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
