package com.SE3_NachhilfeApp.Member;

import com.SE3_NachhilfeApp.Contract.ContractService;
import com.SE3_NachhilfeApp.Offer.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final ContractService contractService;
    private final OfferService offerService;

    private final String doesNotExistMsg = "User does not exist";
    private static final String doesAlreadyExistMsg = "User does already exist";
    private static final String doesHaveContractsMsg = "User still has running contracts and can not be deleted";

    @Autowired
    public MemberService(MemberRepository memberRepository, ContractService contractService, OfferService offerService) {
        this.memberRepository = memberRepository;
        this.contractService = contractService;
        this.offerService = offerService;
    }

    //GET ALL
    public List<Member> getAll(){
        return memberRepository.findAll();
    }

    //GET Member BY ID
    public Member getById(UUID id){
        return memberRepository.findById(id).orElseThrow(() -> new IllegalStateException(doesNotExistMsg));

    }

    //ADD NEW Member
    public void createNew(Member member) {
        Optional<Member> userOptional = memberRepository.findById(member.getId());

        if(userOptional.isPresent()){
            throw new IllegalStateException(doesAlreadyExistMsg);
        }

        memberRepository.save(member);
    }

    //DELETE Member BY ID
    @Transactional
    public void deleteById(UUID id) {
        Member member = memberRepository.findById(id).orElseThrow(() -> new IllegalStateException(doesNotExistMsg));

        if(contractService.getByTutor(id).size() > 0 || contractService.getBySchooler(id).size() > 0){
            throw new IllegalStateException(doesHaveContractsMsg);
        }

        member.setDeleted(true);

        offerService.deleteByMember(id);
    }

    //UPDATE Member BY ID
    @Transactional
    public void updateById(UUID userId, String name, boolean needsHelp, boolean offersHelp) {
        Member member = memberRepository.findById(userId).orElseThrow(() -> new IllegalStateException(doesNotExistMsg));

        if(name != null && name.length() > 0){
            member.setName(name);
        }

        member.setNeedsHelp(needsHelp);

        member.setOffersHelp(offersHelp);
    }
}
