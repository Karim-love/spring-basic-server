package com.karim.spring.basic.server.discount;

import com.karim.spring.basic.server.member.Member;

/**
 * @author : sblim
 * @version : 1.0.0
 * @package : com.karim.springbasicserver.discount
 * @name : spring-basic-server
 * @date : 2023. 02. 02. 002 오후 6:09
 * @modifyed :
 * @description : 정률 할인 정책
 **/
public class ReteDiscountPolicy implements DiscountPolicy{
    @Override
    public int discount(Member member, int price) {
        return 0;
    }
}
