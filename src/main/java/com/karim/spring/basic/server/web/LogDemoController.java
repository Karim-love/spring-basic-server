package com.karim.spring.basic.server.web;

import com.karim.spring.basic.server.common.MyLogger;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author : sblim
 * @version : 1.0.0
 * @package : com.karim.spring.basic.server.web
 * @name : spring-basic-server
 * @date : 2023. 02. 15. 015 오후 6:12
 * @modifyed :
 * @description :
 **/

@Controller
@RequiredArgsConstructor
public class LogDemoController {

    private final LogDemoService logDemoService;
//    private final ObjectProvider<MyLogger> myLoggerObjectProvider;
    private final MyLogger myLogger;

    @RequestMapping("log-demo")
    @ResponseBody
    public String logDemo(HttpServletRequest request) throws InterruptedException {
        String requestURL = request.getRequestURI();

        System.out.println("myLogger = " + myLogger.getClass() );
//        MyLogger myLogger = myLoggerObjectProvider.getObject();
        myLogger.setRequestURL(requestURL);

        myLogger.log("controller test");
        Thread.sleep(1000);
        logDemoService.logic("testId");
        return "OK";
    }
}
