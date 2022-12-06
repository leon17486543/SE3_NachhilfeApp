package com.SE3_NachhilfeApp.Assignment;

import com.SE3_NachhilfeApp.Subjects.Subject;
import com.SE3_NachhilfeApp.Subjects.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AssignmentService {

    private final AssignmentRepository assignmentRepository;

    @Autowired
    public AssignmentService(AssignmentRepository assignmentRepository) {
        this.assignmentRepository = assignmentRepository;
    }

    //GET ALL SUBJECTS
    public List<Assignment> getAssignments(){
        return assignmentRepository.findAll();
    }

    //ADD NEW SUBJECT
    public void addNewAssignment(Assignment assignment) {
        assignmentRepository.save(assignment);
    }

    //TODO DELETE ASSIGNMENT
    /*
    public void deleteStudent(Long id) {
        subjectRepository.findById(id);
        boolean exists = subjectRepository.existsById(id);

        if(!exists){
            throw new IllegalStateException("student does not exist");
        }

        subjectRepository.deleteById(id);
    }
    */

    //TODO UPDATE ASSIGNEMNT
    /*
    @Transactional
    public void updateStudent(Long studentId, String name, String mail) {
        Student student = subjectRepository.findById(studentId).orElseThrow(() -> new IllegalStateException("student does not exist"));

        if(name != null && name.length() > 0 && !Objects.equals(student.getName(), name)){
            student.setName(name);
        }

        if(mail != null && mail.length() > 0 && !Objects.equals(student.getMail(), mail)){
            Optional<Student> studentOptional = studentsRepository.findStudentByEmail(mail);
            if(studentOptional.isPresent()){
                throw new IllegalStateException("mail already taken");
            }
            student.setMail(mail);
        }
    }
    */
}
