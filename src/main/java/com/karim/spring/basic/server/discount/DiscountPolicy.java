package com.karim.spring.basic.server.discount;

import com.karim.spring.basic.server.member.Member;

/**
 * @author : sblim
 * @version : 1.0.0
 * @package : com.karim.springbasicserver.discount
 * @name : spring-basic-server
 * @date : 2023. 02. 02. 002 오후 6:06
 * @modifyed :
 * @description :
 **/
public interface DiscountPolicy {

    /**
     *
     * @param member
     * @param price
     * @return 할인 대상 금액
     */
    int discount(Member member, int price);
}
