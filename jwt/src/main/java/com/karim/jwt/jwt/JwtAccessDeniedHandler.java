package com.karim.jwt.jwt;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author : sblim
 * @version : 1.0.0
 * @package : com.karim.jwt.jwt
 * @name : spring-basic-server
 * @date : 2023. 04. 27. 027 오후 2:13
 * @modifyed :
 * @description : 필요한 권한이 존재하지 않는 겨경우에 403 Forbidden 에러를 리턴하는 class
 **/

@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        //필요한 권한이 없이 접근하려 할때 403
        response.sendError(HttpServletResponse.SC_FORBIDDEN);
    }
}