package com.karim.spring.basic.server.common;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @author : sblim
 * @version : 1.0.0
 * @package : com.karim.spring.basic.server.common
 * @name : spring-basic-server
 * @date : 2023. 02. 15. 015 오후 6:07
 * @modifyed :
 * @description :
 **/

@Component
/**
 * AOP 처럼 CGLIB로 내 클래스를 상속 받은 가짜 프록시 객체를 만들어서 주입한다.
 * 클라이언트가 해당 로직을 호출하면 사실은 가짜 프록시 객체의 메소드를 ㅎㅗ출한 것이다.
 * 가짜 프록시 객체는 request 스코프의 진짜 해당 로직을 호출한다.
 * 가짜 프록시 객체는 원본 클래스를 상속 받아서 만들어졌기 때문에 이 객체를 사용하는 클라이언트 입장에서는 사실 원본인지 아닌지 모르게 동일하게 사용할 수 있다. (다형성)
 */
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MyLogger {

    private String uuid;
    private String requestURL;

    public void setRequestURL(String requestURL) {
        this.requestURL = requestURL;
    }

    public void log(String message){
        System.out.println("["+uuid+"]"+"["+requestURL+"] "+ message);
    }

    @PostConstruct
    public void init(){
        uuid = UUID.randomUUID().toString();
        System.out.println("["+uuid+"] request scope bean create:" + this);
    }

    @PreDestroy
    public void close(){
        System.out.println("["+uuid+"] request scope bean close:" + this);
    }
}
