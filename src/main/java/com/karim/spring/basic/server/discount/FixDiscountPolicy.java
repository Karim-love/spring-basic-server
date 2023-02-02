package com.karim.spring.basic.server.discount;

import com.karim.spring.basic.server.member.Grade;
import com.karim.spring.basic.server.member.Member;

/**
 * @author : sblim
 * @version : 1.0.0
 * @package : com.karim.spring.basic.server.discount
 * @name : spring-basic-server
 * @date : 2023. 02. 02. 002 오후 6:07
 * @modifyed :
 * @description : 고정 할인 정책
 **/
public class FixDiscountPolicy implements DiscountPolicy{

    private int discountFixAmount = 1000; // 1,000원 할인

    @Override
    public int discount(Member member, int price) {

        if ( member.getGrade() == Grade.VIP ){
            return discountFixAmount;
        }else {
            return 0;
        }
    }
}
