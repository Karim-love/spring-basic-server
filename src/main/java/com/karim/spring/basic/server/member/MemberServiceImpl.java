package com.karim.spring.basic.server.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author : sblim
 * @version : 1.0.0
 * @package : com.karim.spring.basic.server.member
 * @name : spring-basic-server
 * @date : 2023. 02. 02. 002 오후 5:43
 * @modifyed :
 * @description : 회원 서비스
 **/

//@Component("memberService2")
@Component
public class MemberServiceImpl implements MemberService {

    //    private final MemberRepository memberRepository = new MemoryMemberRepository();
    private final MemberRepository memberRepository;

    @Autowired // ac.getBean(MemberRepository.class)
    // 1. MemberRepository와 같은 타입의 클래스를 찾는다. (자식 포함)
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }

    //테스트 용도
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
