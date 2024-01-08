package hello.springmvc.basic.request;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

/**
 * @author : sblim
 * @version : 1.0.0
 * @package : hello.springmvc.basic.request
 * @name : spring-basic-server
 * @date : 2023. 08. 31. 031 오후 1:26
 * @modifyed :
 * @description :
 **/

@Slf4j
@RestController
public class RequestHeaderController {

    /**
     *
     * @param request
     * @param response
     * @param httpMethod
     * @param locale
     * @param headerMap MultiValueMap = keyA=value1&keyA=value2...
     * @param host
     * @param cookie
     * @return
     */
    @RequestMapping("/headers")
    public String headers(HttpServletRequest request,
                          HttpServletResponse response,
                          HttpMethod httpMethod,
                          Locale locale,
                          @RequestHeader MultiValueMap<String, String> headerMap,
                          @RequestHeader("host") String host,
                          @CookieValue(value = "myCookie", required = false) String cookie ) {

        log.info("request = {}", request);
        log.info("response = {}", response);
        log.info("httpMethod = {}", httpMethod);
        log.info("locale = {}", locale);
        log.info("headerMap = {}", headerMap);
        log.info("host = {}", host);
        log.info("myCookie = {}", cookie);

        return "ok";
    }
}
