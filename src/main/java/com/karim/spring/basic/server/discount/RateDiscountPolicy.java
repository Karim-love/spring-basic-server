package com.karim.spring.basic.server.discount;

import com.karim.spring.basic.server.annotation.MainDiscountPolicy;
import com.karim.spring.basic.server.member.Grade;
import com.karim.spring.basic.server.member.Member;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 * @author : sblim
 * @version : 1.0.0
 * @package : com.karim.spring.basic.server.discount
 * @name : spring-basic-server
 * @date : 2023. 02. 02. 002 오후 6:09
 * @modifyed :
 * @description : 정률 할인 정책
 **/

@Component
//@Qualifier("mainDiscountPolicy")
@MainDiscountPolicy
@Primary
public class RateDiscountPolicy implements DiscountPolicy{

    private int discountPercent = 10; // 10% 할인

    @Override
    public int discount(Member member, int price) {
        if (member.getGrade() == Grade.VIP) {
            return price * discountPercent / 100;
        } else {
            return 0;
        }
    }
}
