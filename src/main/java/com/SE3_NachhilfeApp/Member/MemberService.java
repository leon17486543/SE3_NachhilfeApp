package com.SE3_NachhilfeApp.Member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    //GET ALL
    public List<Member> getAll(){
        return memberRepository.findAll();
    }

    //GET Member BY ID
    public Member getById(UUID id){
        return memberRepository.findById(id).orElseThrow(() -> new IllegalStateException("member does not exist"));

    }

    //ADD NEW Member
    public void createNew(Member member) {
        Optional<Member> userOptional = memberRepository.findMemberByName(member.getName());

        if(userOptional.isPresent()){
            throw new IllegalStateException("user already exists");
        }

        memberRepository.save(member);
    }

    //DELETE Member BY ID
    public void deleteById(UUID id) {
        memberRepository.findById(id);
        boolean exists = memberRepository.existsById(id);

        if(!exists){
            throw new IllegalStateException("user does not exist");
        }

        memberRepository.deleteById(id);
    }

    //UPDATE Member BY ID
    @Transactional
    public void updateById(UUID userId, String name, boolean needsHelp, boolean offersHelp) {
        Member member = memberRepository.findById(userId).orElseThrow(() -> new IllegalStateException("user does not exist"));

        if(name != null && name.length() > 0){
            member.setName(name);
        }

        if(needsHelp || !needsHelp){
            member.setNeedsHelp(needsHelp);
        }

        if(offersHelp || !offersHelp){
            member.setOffersHelp(offersHelp);
        }
    }
}
