package com.karim.spring.basic.server.member;

import com.karim.spring.basic.server.AppConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author : sblim
 * @version : 1.0.0
 * @package : com.karim.spring.basic.server.member
 * @name : spring-basic-server
 * @date : 2023. 02. 02. 002 오후 5:49
 * @modifyed :
 * @description : memberService 테스트 코드 모임 class 입니다.
 **/
public class MemberServiceTest {

    MemberService memberService;

    @BeforeEach
    public void beforeEach() {
        AppConfig appConfig = new AppConfig();
        memberService = appConfig.memberService();
    }

    @Test
    void join(){
        //given
        Member member = new Member(1L, "memberA", Grade.BASIC);

        //when
        memberService.join(member);
        Member findMember = memberService.findMember(1L);

        //then
        // member랑 findMember가 같은 지 확인
        Assertions.assertThat(member).isEqualTo(findMember);
    }
}
