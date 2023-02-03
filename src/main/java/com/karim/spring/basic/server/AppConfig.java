package com.karim.spring.basic.server;

import com.karim.spring.basic.server.discount.DiscountPolicy;
import com.karim.spring.basic.server.discount.FixDiscountPolicy;
import com.karim.spring.basic.server.discount.RateDiscountPolicy;
import com.karim.spring.basic.server.member.Member;
import com.karim.spring.basic.server.member.MemberService;
import com.karim.spring.basic.server.member.MemberServiceImpl;
import com.karim.spring.basic.server.member.MemoryMemberRepository;
import com.karim.spring.basic.server.order.OrderService;
import com.karim.spring.basic.server.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : sblim
 * @version : 1.0.0
 * @package : com.karim.spring.basic.server
 * @name : spring-basic-server
 * @date : 2023. 02. 03. 003 오후 3:00
 * @modifyed :
 * @description : 애플리케이션의 실제 동작에 필요한 구현 객체를 생성한다.
 *              : 생성한 객체 인스턴스의 참조(래퍼런스)를 생성자를 통해서 주입(연결)해준다.
 * ==> DIP 완성!!
 * ==> 관심사의 분리 : 객체를 생성하고 연결하는 역할과 실행하는 역할이 명확히 분리되었다.
 **/

@Configuration
public class AppConfig {

    /**
     * 회원 서비스 역할
     * @return
     */
    @Bean
    public MemberService memberService(){
        return new MemberServiceImpl(memberRepository());
    }

    /**
     * 회원 저장소 역할
     * @return
     */
    @Bean
    public MemoryMemberRepository memberRepository() {
        return new MemoryMemberRepository(); // 메모리 구현
    }

    /**
     * 주문 서비스 역할
     * @return
     */
    @Bean
    public OrderService orderService(){
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    /**
     * 할인 정책 역할
     * @return
     */
    @Bean
    public DiscountPolicy discountPolicy(){
//        return new FixDiscountPolicy(); //고정 할인 구현
        return new RateDiscountPolicy(); //정률 할인 구현
    }
}
