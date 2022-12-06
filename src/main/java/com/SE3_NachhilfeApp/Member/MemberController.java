package com.SE3_NachhilfeApp.Member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/user")
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService){
        this.memberService = memberService;
    }

    @GetMapping()
    public List<Member> getMember(){
        return memberService.getMember();
    }

    @PostMapping
    public void createNewMember(@RequestBody Member member){
        memberService.addNewMember(member);
    }

    //TODO DELETE Member
    //DELETE
    /*
    @DeleteMapping(path = "{studentId}")
    public void deleteStudent(@PathVariable("studentId") Long id){
        subjectService.deleteStudent(id);
    }
     */

    //TODO UPDATE Member
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
