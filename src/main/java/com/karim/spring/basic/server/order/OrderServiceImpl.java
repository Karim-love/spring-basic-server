package com.karim.spring.basic.server.order;

import com.karim.spring.basic.server.discount.DiscountPolicy;
import com.karim.spring.basic.server.discount.FixDiscountPolicy;
import com.karim.spring.basic.server.member.MemberRepository;
import com.karim.spring.basic.server.member.MemoryMemberRepository;
import com.karim.spring.basic.server.member.Member;

/**
 * @author : sblim
 * @version : 1.0.0
 * @package : com.karim.springbasicserver.order
 * @name : spring-basic-server
 * @date : 2023. 02. 02. 002 오후 6:15
 * @modifyed :
 * @description :
 **/
public class OrderServiceImpl implements OrderService{

    private final MemberRepository memberRepository = new MemoryMemberRepository();
    private final DiscountPolicy discountPolicy = new FixDiscountPolicy();

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {

        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }
}
