package com.SE3_NachhilfeApp.Member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/v1/user")
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService){
        this.memberService = memberService;
    }

    //GET ALL
    @GetMapping()
    public List<Member> getMember(){
        return memberService.getMember();
    }

    //GET BY ID
    @GetMapping(path = "byId/{userId}")
    public Member getMemberById(@PathVariable("userId") UUID userId){
        return memberService.getMemberById(userId);
    }

    //ADD NEW
    @PostMapping(path = "add")
    public void createNewMember(@RequestBody Member member){
        memberService.addNewMember(member);
    }

    //DELETE BY ID
    @DeleteMapping(path = "delete/{userId}")
    public void deleteStudent(@PathVariable("userId") UUID userId){
        memberService.deleteMember(userId);
    }

    //UPDATE BY ID
    @PutMapping(path = "update/{userId}")
    public void updateMember(@PathVariable("userId") UUID userId,
                             @RequestParam(required = false) String name,
                             @RequestParam(required = false) boolean needsHelp,
                             @RequestParam(required = false) boolean offersHelp){
        memberService.updateMember(userId, name, needsHelp, offersHelp);
    }
}
