package com.karim.jwt.jwt;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author : sblim
 * @version : 1.0.0
 * @package : com.karim.jwt.jwt
 * @name : spring-basic-server
 * @date : 2023. 04. 27. 027 오전 11:34
 * @modifyed :
 * @description : 유효한 자격증명을 제공하지 않고 접근하려 할때 401 Unauthorized 에러를 리턴하는 class
 **/
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        // 유효한 자격증명을 제공하지 않고 접근하려 할때 401
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
