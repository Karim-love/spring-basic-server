package com.karim.spring.basic.server.beanfind;

import com.karim.spring.basic.server.member.MemberRepository;
import com.karim.spring.basic.server.member.MemoryMemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class ApplicationContextSameBeanFindTest {

    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(SameConfig.class);

    @Test
    @DisplayName("타입으로 초회시 같은 타입이 둘 이상 있으먄, 중복 오류가 발생한다.")
        // 역할의 의존
    void findBeanByTypeDuplicate(){
//        MemberRepository memberRepository = ac.getBean( MemberRepository.class);

        Assertions.assertThrows(NoUniqueBeanDefinitionException.class,
                () -> ac.getBean(MemberRepository.class));
    }

    @Test
    @DisplayName("타입으로 초회시 같은 타입이 둘 이상 있으먄, 빈 이름을 지정하면 된다.")
        // 역할의 의존
    void findBeanByName(){
        MemberRepository memberRepository = ac.getBean( "memberRepository1", MemberRepository.class);
        org.assertj.core.api.Assertions.assertThat(memberRepository).isInstanceOf(MemberRepository.class);
    }

    @Test
    @DisplayName("타입으로 초회시 같은 타입이 둘 이상 있으먄, 빈 이름을 지정하면 된다.")
    void findAllBeanByType(){
        Map<String, MemberRepository> beansOfType = ac.getBeansOfType(  MemberRepository.class);
        for (String s : beansOfType.keySet()) {
            System.out.println("key = " + s + " value = " + beansOfType.get(s));
        }
        System.out.println("beanOfType = " + beansOfType);
        org.assertj.core.api.Assertions.assertThat(beansOfType.size()).isEqualTo(2);
    }

    @Configuration
    static class SameConfig{

        @Bean
        public MemberRepository memberRepository1(){
            return new MemoryMemberRepository();
        }

        @Bean
        public MemberRepository memberRepository2(){
            return new MemoryMemberRepository();
        }
    }

}
