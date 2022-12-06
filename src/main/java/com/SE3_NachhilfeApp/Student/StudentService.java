package com.SE3_NachhilfeApp.Student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentsRepository studentsRepository;

    @Autowired
    public StudentService(StudentsRepository studentsRepository) {
        this.studentsRepository = studentsRepository;
    }

    public List<Student> getStudents(){
        return studentsRepository.findAll();
    }

    public void addNewStudent(Student student) {
        Optional<Student> studentOptional = studentsRepository.findStudentByEmail(student.getMail());

        if(studentOptional.isPresent()){
            throw new IllegalStateException("email taken");
        }

        studentsRepository.save(student);

        System.out.println(student);

    }

    public void deleteStudent(Long id) {
        studentsRepository.findById(id);
        boolean exists = studentsRepository.existsById(id);

        if(!exists){
            throw new IllegalStateException("student does not exist");
        }

        studentsRepository.deleteById(id);
    }

    @Transactional
    public void updateStudent(Long studentId, String name, String mail) {
        Student student = studentsRepository.findById(studentId).orElseThrow(() -> new IllegalStateException("student does not exist"));

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
}
