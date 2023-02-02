package com.karim.spring.basic.server.member;

/**
 * @author : sblim
 * @version : 1.0.0
 * @package : com.karim.springbasicserver.member
 * @name : spring-basic-server
 * @date : 2023. 02. 02. 002 오후 5:41
 * @modifyed :
 * @description :
 **/
public interface MemberService {

    void join(Member member);
    Member findMember(Long memberId);
}
