package com.karim.spring.basic.server;

import com.karim.spring.basic.server.member.MemberService;
import com.karim.spring.basic.server.member.MemberServiceImpl;
import com.karim.spring.basic.server.member.Grade;
import com.karim.spring.basic.server.member.Member;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author : sblim
 * @version : 1.0.0
 * @package : com.karim.spring.basic.server
 * @name : spring-basic-server
 * @date : 2023. 02. 02. 002 오후 5:45
 * @modifyed :
 * @description : 스프링 핵심 원리 이해 테스트 코드 실행 class 입니다.
 **/
public class MemberApp {

    public static void main(String[] args) {
//        AppConfig appConfig = new AppConfig();
//        MemberService memberService = appConfig.memberService();

        /**
         * 스프링 컨테이너 ( 모든 빈 관리 )
         * getBean : AppConfig에 메소드 이름을 Key, 반환 타입 value
         */
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        MemberService memberService = applicationContext.getBean("memberService", MemberService.class);

        Member memberA = new Member(1L, "memberA", Grade.VIP);
        memberService.join(memberA);

        Member findMember = memberService.findMember(1L);
        System.out.println( "new member = " + memberA.getName() );
        System.out.println( "find member = " + findMember.getName() );
    }
}
