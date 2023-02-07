package com.karim.spring.basic.server.singleton;

import com.karim.spring.basic.server.AppConfig;
import com.karim.spring.basic.server.member.MemberRepository;
import com.karim.spring.basic.server.member.MemberServiceImpl;
import com.karim.spring.basic.server.order.OrderServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author : sblim
 * @version : 1.0.0
 * @package : com.karim.spring.basic.server.singleton
 * @name : spring-basic-server
 * @date : 2023. 02. 07. 007 오후 5:35
 * @modifyed :
 * @description :
 **/
public class ConfigurationSingletonTest {

    @Test
    void configurationTest(){

        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        MemberServiceImpl memberService = ac.getBean("memberService", MemberServiceImpl.class);
        OrderServiceImpl orderService = ac.getBean("orderService", OrderServiceImpl.class);
        MemberRepository memberRepository = ac.getBean("memberRepository", MemberRepository.class);

        MemberRepository memberRepository1 = memberService.getMemberRepository();
        MemberRepository memberRepository2 = orderService.getMemberRepository();

        System.out.println("memberRepository1 = " + memberRepository1);
        System.out.println("memberRepository2 = " + memberRepository2);
        System.out.println("memberRepository = " + memberRepository);

        Assertions.assertThat(memberService.getMemberRepository()).isSameAs(memberRepository1);
        Assertions.assertThat(memberService.getMemberRepository()).isSameAs(memberRepository2);
        
    }

    /**
     * getBean sout 시 순수 클래스가 나오길 기대 하지만.
     * 결과적으로는 xxxCGLIB가 붙은 클래스가 나온다.
     *  => bean = com.karim.spring.basic.server.AppConfig$$SpringCGLIB$$0@13ad5cd3
     * 이유!!
     * 이것은 내가 만든 클래스가 아니라 스프링이 CGLIB라는 바이트코드 조작 라이브러리를 사용해서
     * AppConfig 클래스를 상속받은 임의의 다른 클래스를 만들고, 그 다른 클래스를 스프링 빈으로 등록한 것이다.
     * 해당 임의의 다른 클래스가 바론 싱글톤이 보장되도록 해준다.
     */
    @Test
    void configurationDeep(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        AppConfig bean = ac.getBean(AppConfig.class);
        System.out.println("bean = " + bean);
    }
}
