package com.demo.redis_spring.type.list.controller;

import com.demo.redis_spring.model.RedisRequest;
import com.demo.redis_spring.service.RedisDispatcherService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author : sblim
 * @version : 1.0.0
 * @package : com.demo.redis_spring.list.controller
 * @name : spring-basic-server
 * @date : 2025. 07. 29. 화 오후 4:39
 * @modifyed :
 * @description :
 **/

/**
 * Redis List 타입 데이터에 대한 웹 요청을 처리하는 컨트롤러입니다.
 */
@RestController
@RequestMapping("/redis/list")
public class RedisListController {

    private final RedisDispatcherService redisDispatcherService;

    public RedisListController(RedisDispatcherService redisDispatcherService) {
        this.redisDispatcherService = redisDispatcherService;
    }

    /**
     * 리스트의 오른쪽(꼬리)에 요소를 추가합니다. 요청 바디에 RedisRequest 객체를 받습니다.
     * @param request 키와 값을 포함하는 RedisRequest 객체
     * @return 확인 메시지
     */
    @PostMapping("/addRight")
    public String addToListRight(@RequestBody RedisRequest request) {
        if (request.getKey() == null || request.getValue() == null) {
            return "Error: Key and value are required for list operation.";
        }
        redisDispatcherService.addToListRight(request.getKey(), request.getValue());
        return "Added item to list (right) successfully!";
    }

    /**
     * 리스트의 왼쪽(머리)에 요소를 추가합니다. 요청 바디에 RedisRequest 객체를 받습니다.
     * @param request 키와 값을 포함하는 RedisRequest 객체
     * @return 확인 메시지
     */
    @PostMapping("/addLeft")
    public String addToListLeft(@RequestBody RedisRequest request) {
        if (request.getKey() == null || request.getValue() == null) {
            return "Error: Key and value are required for list operation.";
        }
        redisDispatcherService.addToListLeft(request.getKey(), request.getValue());
        return "Added item to list (left) successfully!";
    }

    /**
     * 리스트의 모든 요소를 가져옵니다. URL 쿼리 파라미터로 키를 받습니다.
     * @param key 리스트의 키
     * @return 리스트의 모든 요소들
     */
    @GetMapping("/all")
    public List<Object> getAllList(@RequestParam(value = "key") String key) {
        return redisDispatcherService.getListRange(key, 0, -1);
    }

    /**
     * 리스트의 특정 범위 요소를 가져옵니다. URL 쿼리 파라미터로 키, 시작 인덱스, 끝 인덱스를 받습니다.
     * @param key 리스트의 키
     * @param start 시작 인덱스
     * @param end 끝 인덱스
     * @return 리스트의 요소들
     */
    @GetMapping("/range")
    public List<Object> getListRange(@RequestParam(value = "key") String key, @RequestParam(value = "start") long start, @RequestParam(value = "end") long end) {
        return redisDispatcherService.getListRange(key, start, end);
    }

    /**
     * 리스트의 길이를 가져옵니다. URL 쿼리 파라미터로 키를 받습니다.
     * @param key 리스트의 키
     * @return 리스트 길이
     */
    @GetMapping("/size")
    public Long getListSize(@RequestParam(value = "key") String key) {
        return redisDispatcherService.getListSize(key);
    }

    /**
     * 리스트의 왼쪽 끝에서 요소를 제거하고 반환합니다. URL 쿼리 파라미터로 키를 받습니다.
     * @param key 리스트의 키
     * @return 제거된 요소
     */
    @DeleteMapping("/popLeft")
    public Object popListLeft(@RequestParam(value = "key") String key) {
        return redisDispatcherService.popListLeft(key);
    }

    /**
     * 리스트의 오른쪽 끝에서 요소를 제거하고 반환합니다. URL 쿼리 파라미터로 키를 받습니다.
     * @param key 리스트의 키
     * @return 제거된 요소
     */
    @DeleteMapping("/popRight")
    public Object popListRight(@RequestParam(value = "key") String key) {
        return redisDispatcherService.popListRight(key);
    }
}
