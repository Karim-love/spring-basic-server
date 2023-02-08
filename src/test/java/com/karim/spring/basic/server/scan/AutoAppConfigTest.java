package com.karim.spring.basic.server.scan;

import com.karim.spring.basic.server.AppConfig;
import com.karim.spring.basic.server.AutoAppConfig;
import com.karim.spring.basic.server.member.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Component;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author : sblim
 * @version : 1.0.0
 * @package : com.karim.spring.basic.server.scan
 * @name : spring-basic-server
 * @date : 2023. 02. 08. 008 오후 1:13
 * @modifyed :
 * @description :
 **/

class AutoAppConfigTest {

    @Test
    void basicScan(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class);

        MemberService memberService = ac.getBean(MemberService.class);
        Assertions.assertThat(memberService).isInstanceOf(MemberService.class);
    }
}