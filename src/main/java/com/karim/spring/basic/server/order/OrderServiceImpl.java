package com.karim.spring.basic.server.order;

import com.karim.spring.basic.server.discount.DiscountPolicy;
import com.karim.spring.basic.server.discount.RateDiscountPolicy;
import com.karim.spring.basic.server.member.Member;
import com.karim.spring.basic.server.member.MemberRepository;
import com.karim.spring.basic.server.member.MemoryMemberRepository;

/**
 * @author : sblim
 * @version : 1.0.0
 * @package : com.karim.spring.basic.server.order
 * @name : spring-basic-server
 * @date : 2023. 02. 02. 002 오후 6:15
 * @modifyed :
 * @description :
 **/
public class OrderServiceImpl implements OrderService {

    /**
     * 지금 코드는 기능을 확장해서 변경하면, 클라이언트 코드에 영향을 준다 따라서 !!OCP 위반!!
     * OrderServiceImpl가 구체 class 인 Fix, Rate 둘다 의존 !!DIP 위반!!
     */
//    private final MemberRepository memberRepository = new MemoryMemberRepository();
//    private final DiscountPolicy discountPolicy = new FixDiscountPolicy(); // 고정 할인 금액
//    private final DiscountPolicy discountPolicy = new RateDiscountPolicy(); // 정률 할인 금액

    /**
     * ##해결 방법##
     * 이 문제를 해결하려면 누군가가 클라이언트인 OraderServiceImpl에 DiscountPolicy의 구현 객체를 대신 생성하고 주입해주어야 한다.
     * AppConfig
     * : 애플리케이션의 전체 동작 방식을 구성(config)하기 위해, 구현 객체를 생성하고 연결하는 책임을 가지는 별도의 설정 클래스 생성
     */
    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {

        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }
}
