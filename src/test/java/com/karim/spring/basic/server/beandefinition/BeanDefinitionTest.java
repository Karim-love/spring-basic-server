package com.karim.spring.basic.server.beandefinition;

import com.karim.spring.basic.server.AppConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

/**
 * @author : sblim
 * @version : 1.0.0
 * @package : com.karim.spring.basic.server.beandefinition
 * @name : spring-basic-server
 * @date : 2023. 02. 07. 007 오후 4:10
 * @modifyed :
 * @description :
 **/
public class BeanDefinitionTest {

//    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig .class);
    GenericXmlApplicationContext applicationContext = new GenericXmlApplicationContext("appConfig.xml");

    @Test
    @DisplayName(" 빈 설정 메타정보 확인")
    void findApplicationBean(){
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            // applicationContext가 인터페이스 ApplicationConext로 선언이 되면 getBeanDefinition을 사용하지 못함
            // -> 구체 인터페이스가 다른 인터페이스에서 가져오는 것임
            BeanDefinition beanDefinition = applicationContext.getBeanDefinition(beanDefinitionName);

            if (beanDefinition.getRole() == BeanDefinition.ROLE_APPLICATION){
                System.out.println("beanDefinitionName = " + beanDefinitionName +
                " beanDefinition = " + beanDefinition);
            }

        }
    }
}
