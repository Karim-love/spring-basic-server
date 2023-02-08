package com.karim.spring.basic.server.member;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author : sblim
 * @version : 1.0.0
 * @package : com.karim.spring.basic.server.member
 * @name : spring-basic-server
 * @date : 2023. 02. 02. 002 오후 5:39
 * @modifyed :
 * @description :
 **/

@Component
public class MemoryMemberRepository implements MemberRepository{

    private static Map<Long, Member> store = new ConcurrentHashMap<>();

    @Override
    public void save(Member member) {
        store.put(member.getId(), member);
    }

    @Override
    public Member findById(Long memberId) {
        return store.get(memberId);
    }
}
