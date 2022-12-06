package com.SE3_NachhilfeApp.Member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    //GET ALL Member
    public List<Member> getMember(){
        return memberRepository.findAll();
    }

    //ADD NEW Member
    public void addNewMember(Member member) {
        Optional<Member> userOptional = memberRepository.findMemberByName(member.getName());

        if(userOptional.isPresent()){
            throw new IllegalStateException("user already exists");
        }

        memberRepository.save(member);
    }

    //TODO DELETE Member
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

    //TODO UPDATE Member
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
