package com.karim.spring.basic.server;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

/**
 * @author : sblim
 * @version : 1.0.0
 * @package : com.karim.spring.basic.server
 * @name : spring-basic-server
 * @date : 2023. 02. 08. 008 오후 1:04
 * @modifyed :
 * @description :
 **/

@Configuration
//@Component 가 붙은 클래스를 스캔해서 스프링 빈으로 등록
@ComponentScan(
        // 나머지 @Configuration이 붙은 예제 AppConfig.java 등.. 제외
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
)
public class AutoAppConfig {
}
