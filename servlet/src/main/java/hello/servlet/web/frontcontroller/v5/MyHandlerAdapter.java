package hello.servlet.web.frontcontroller.v5;

import hello.servlet.web.frontcontroller.ModelView;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * @author : sblim
 * @version : 1.0.0
 * @package : hello.servlet.web.frontcontroller.v5
 * @name : spring-basic-server
 * @date : 2023. 08. 25. 025 오전 10:13
 * @modifyed :
 * @description : 어댑터는 실제 컨트롤러를 호출하고, 그 결과로 ModelView를 반환한다.
 *                실제 컨트롤러가 ModelView를 반환하지 못하며느 어댑터가 직접 생성해서라도 반환해야 한다.
 **/
public interface MyHandlerAdapter {

    // 해당 핸들러 처리 여부 반환
    boolean supports(Object handler);

    ModelView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException, IOException;
}
