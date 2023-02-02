package com.karim.spring.basic.server.member;

/**
 * @author : sblim
 * @version : 1.0.0
 * @package : com.karim.springbasicserver.member
 * @name : spring-basic-server
 * @date : 2023. 02. 02. 002 오후 5:37
 * @modifyed :
 * @description :
 **/

public interface MemberRepository {

    void save(Member member);

    Member findById(Long memberId);
}
