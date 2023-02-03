package com.karim.spring.basic.server.discount;

import com.karim.spring.basic.server.member.Grade;
import com.karim.spring.basic.server.member.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author : sblim
 * @version : 1.0.0
 * @package : com.karim.spring.basic.server.discount
 * @name : spring-basic-server
 * @date : 2023. 02. 03. 003 오후 2:34
 * @modifyed :
 * @description :
 **/
class RateDiscountPolicyTest {

    DiscountPolicy discountPolicy = new RateDiscountPolicy();

    @Test
    @DisplayName("VIP는 10% 할인이 적용되어야 한다.")
    void vip_o(){
        //given
        Member member = new Member(1L, "memberA", Grade.VIP);
        // when
        int discount = discountPolicy.discount(member, 10000);
        //then
        assertThat(discount).isEqualTo(1000);
    }

    @Test
    @DisplayName("VIP가 아니면 10% 할인이 적용되어 않아야 한다.")
    void vip_x(){
        //given
        Member member = new Member(1L, "memberB", Grade.BASIC);
        // when
        int discount = discountPolicy.discount(member, 10000);
        //then
        assertThat(discount).isEqualTo(1000);
    }
}