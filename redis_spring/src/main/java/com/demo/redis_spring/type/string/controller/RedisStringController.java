package com.demo.redis_spring.type.string.controller;

import com.demo.redis_spring.model.RedisDataType;
import com.demo.redis_spring.model.RedisRequest;
import com.demo.redis_spring.service.RedisDispatcherService;
import org.springframework.web.bind.annotation.*;

/**
 * @author : sblim
 * @version : 1.0.0
 * @package : com.demo.redis_spring.list.controller
 * @name : spring-basic-server
 * @date : 2025. 07. 29. 화 오후 4:38
 * @modifyed :
 * @description :
 **/

/**
 * Redis String 타입 데이터에 대한 웹 요청을 처리하는 컨트롤러입니다.
 */
@RestController
@RequestMapping("/redis/string")
public class RedisStringController {

    private final RedisDispatcherService redisDispatcherService;

    public RedisStringController(RedisDispatcherService redisDispatcherService) {
        this.redisDispatcherService = redisDispatcherService;
    }
}
