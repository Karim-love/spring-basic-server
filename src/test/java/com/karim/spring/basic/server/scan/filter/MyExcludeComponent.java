package com.karim.spring.basic.server.scan.filter;

import java.lang.annotation.*;

/**
 * @author : sblim
 * @version : 1.0.0
 * @package : com.karim.spring.basic.server.scan.filter
 * @name : spring-basic-server
 * @date : 2023. 02. 08. 008 오후 2:00
 * @modifyed :
 * @description : 내가 만든 어노테이션
 **/

@Target(ElementType.TYPE) // 중요
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyExcludeComponent {
}
