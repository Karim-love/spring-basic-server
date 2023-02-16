package com.karim.spring.basic.server.web;

import com.karim.spring.basic.server.common.MyLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

/**
 * @author : sblim
 * @version : 1.0.0
 * @package : com.karim.spring.basic.server.web
 * @name : spring-basic-server
 * @date : 2023. 02. 15. 015 오후 6:14
 * @modifyed :
 * @description :
 **/

@Service
@RequiredArgsConstructor
public class LogDemoService {

//    private final ObjectProvider<MyLogger> myLoggerObjectProvider;
    private final MyLogger myLogger;
    public void logic(String id) {
//        MyLogger myLogger = myLoggerObjectProvider.getObject();
        myLogger.log("service id = " + id);
    }
}
