package com.karim.spring.basic.server.xml;

import com.karim.spring.basic.server.member.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author : sblim
 * @version : 1.0.0
 * @package : com.karim.spring.basic.server.xml
 * @name : spring-basic-server
 * @date : 2023. 02. 07. 007 오후 3:15
 * @modifyed :
 * @description :
 **/
class XmlAppContext {

    @Test
    void xmlAppContext(){
        ApplicationContext genericXmlApplicationContext = new GenericXmlApplicationContext("appConfig.xml");
        MemberService memberService = genericXmlApplicationContext.getBean("memberService", MemberService.class);
        assertThat(memberService).isInstanceOf(MemberService.class);
    }
}
