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
    public List<Member> getAll(){
        return memberService.getAll();
    }

    //GET BY ID
    @GetMapping(path = "byId/{userId}")
    public Member getById(@PathVariable("userId") UUID userId){
        return memberService.getById(userId);
    }

    //ADD NEW
    @PostMapping(path = "add")
    public void createNew(@RequestBody Member member){
        memberService.createNew(member);
    }

    //DELETE BY ID
    @DeleteMapping(path = "delete/{userId}")
    public void deleteById(@PathVariable("userId") UUID userId){
        memberService.deleteById(userId);
    }

    //UPDATE BY ID
    @PutMapping(path = "update/{userId}")
    public void updateById(@PathVariable("userId") UUID userId,
                             @RequestParam(required = false) String name,
                             @RequestParam(required = false) boolean needsHelp,
                             @RequestParam(required = false) boolean offersHelp){
        memberService.updateById(userId, name, needsHelp, offersHelp);
    }
}
