package com.karim.spring.basic.server;

import com.karim.spring.basic.server.member.Grade;
import com.karim.spring.basic.server.member.MemberService;
import com.karim.spring.basic.server.member.MemberServiceImpl;
import com.karim.spring.basic.server.order.Order;
import com.karim.spring.basic.server.order.OrderService;
import com.karim.spring.basic.server.order.OrderServiceImpl;
import com.karim.spring.basic.server.member.Member;

/**
 * @author : sblim
 * @version : 1.0.0
 * @package : com.karim.spring.basic.server
 * @name : spring-basic-server
 * @date : 2023. 02. 02. 002 오후 6:21
 * @modifyed :
 * @description :
 **/
public class OrderApp {

    public static void main(String[] args) {

        AppConfig appConfig = new AppConfig();
        MemberService memberService = appConfig.memberService();
        OrderService orderService = appConfig.orderService();

        Long memberId = 1L;
        Member member = new Member(memberId, "memberA", Grade.VIP);
        memberService.join(member);

        Order order = orderService.createOrder(memberId, "itemA", 10000);

        System.out.println("order = " + order );
    }
}
