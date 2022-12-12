package com.SE3_NachhilfeApp.Subjects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

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

    //ADD NEW SUBJECT
    public void addNewSubject(Subject subject) {
        Optional<Subject> subjectOptional = subjectRepository.findSubjectByName(subject.getName());

        if(subjectOptional.isPresent()){
            throw new IllegalStateException("subject already exists");
        }

        subjectRepository.save(subject);

        System.out.println(subject);
    }

    //TODO DELET SUBJECT
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

    //TODO UPDATE SUBJECT
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
