package com.demo.redis_spring.type.hash.controller;

import com.demo.redis_spring.model.RedisRequest;
import com.demo.redis_spring.service.RedisDispatcherService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author : sblim
 * @version : 1.0.0
 * @package : com.demo.redis_spring.hash.controller
 * @name : spring-basic-server
 * @date : 2025. 07. 29. 화 오후 4:45
 * @modifyed :
 * @description :
 **/

/**
 * Redis Hash 타입 데이터에 대한 웹 요청을 처리하는 컨트롤러입니다.
 */
@RestController
@RequestMapping("/redis/hash")
public class RedisHashController {

    private final RedisDispatcherService redisDispatcherService;

    public RedisHashController(RedisDispatcherService redisDispatcherService) {
        this.redisDispatcherService = redisDispatcherService;
    }

    /**
     * Hash에 단일 필드-값을 저장합니다. 요청 바디에 RedisRequest 객체를 받습니다.
     * @param request 키, 필드, 값을 포함하는 RedisRequest 객체.
     * @return 확인 메시지
     */
    @PostMapping("/put")
    public String putHashEntry(@RequestBody RedisRequest request) {

        if (request.getKey() == null || request.getField() == null || request.getValue() == null) {
            return "Error: Key, field, and value are required for putting hash entry.";
        }
        redisDispatcherService.putHashEntry(request.getKey(), request.getField(), request.getValue());
        return "Put entry into Hash successfully!";
    }

    /**
     * Hash에서 특정 필드의 값을 가져옵니다. URL 쿼리 파라미터로 키와 필드를 받습니다.
     * @param key Hash의 키
     * @param field 가져올 필드 키
     * @return 해당 필드의 값
     */
    @GetMapping("/getEntry")
    public Object getHashEntry(@RequestParam(value = "key") String key, @RequestParam(value = "field") String field) {
        return redisDispatcherService.getHashEntry(key, field);
    }

    /**
     * Hash의 모든 필드-값 쌍을 가져옵니다. URL 쿼리 파라미터로 키를 받습니다.
     * @param key Hash의 키
     * @return Hash의 모든 필드-값 쌍 (Map<String, Object>)
     */
    @GetMapping("/allEntries")
    public Map<String, Object> getAllHashEntries(@RequestParam(value = "key") String key) {
        return redisDispatcherService.getAllHashEntries(key);
    }

    /**
     * Hash에서 하나 이상의 필드를 제거합니다. 요청 바디에 RedisRequest 객체를 받습니다.
     * @param request 키와 제거할 필드를 포함하는 RedisRequest 객체.
     * @return 확인 메시지
     */
    @DeleteMapping("/removeField")
    public String removeHashField(@RequestBody RedisRequest request) {
        if (request.getKey() == null || request.getField() == null) {
            return "Error: Key and field are required for removing hash field.";
        }
        redisDispatcherService.deleteHashFields(request.getKey(), request.getField());
        return "Removed field from Hash successfully!";
    }

    /**
     * Hash에 특정 필드가 존재하는지 확인합니다. URL 쿼리 파라미터로 키와 필드를 받습니다.
     * @param key Hash의 키
     * @param field 확인할 필드 키
     * @return 필드 존재 여부
     */
    @GetMapping("/hasField")
    public boolean hasHashField(@RequestParam(value = "key") String key, @RequestParam(value = "field") String field) {
        return redisDispatcherService.hasHashField(key, field);
    }

    /**
     * Hash의 필드 수를 가져옵니다. URL 쿼리 파라미터로 키를 받습니다.
     * @param key Hash의 키
     * @return Hash의 필드 수
     */
    @GetMapping("/size")
    public Long getHashSize(@RequestParam(value = "key") String key) {
        return redisDispatcherService.getHashSize(key);
    }
}
