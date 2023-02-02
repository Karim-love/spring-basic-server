package com.karim.spring.basic.server.order;

import com.karim.spring.basic.server.member.MemberService;
import com.karim.spring.basic.server.member.Grade;
import com.karim.spring.basic.server.member.Member;
import com.karim.spring.basic.server.member.MemberServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author : sblim
 * @version : 1.0.0
 * @package : com.karim.spring.basic.server.order
 * @name : spring-basic-server
 * @date : 2023. 02. 02. 002 오후 6:26
 * @modifyed :
 * @description :
 **/
class OrderServiceTest {

    MemberService memberService = new MemberServiceImpl();
    OrderService orderService = new OrderServiceImpl();

    @Test
    void createOrder(){
        long memberId = 1L;

        Member member = new Member(memberId, "memberA", Grade.VIP);
        memberService.join(member);

        Order order = orderService.createOrder(memberId, "itemA", 10000);

        Assertions.assertThat(order.getDiscountPrice()).isEqualTo(1000);
    }
}