package com.demo.redis_spring.controller;

import com.demo.redis_spring.model.RedisDataType;
import com.demo.redis_spring.model.RedisRequest;
import com.demo.redis_spring.service.RedisDispatcherService;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * @author : sblim
 * @version : 1.0.0
 * @package : controller
 * @name : spring-basic-server
 * @date : 2025. 07. 29. 화 오후 5:25
 * @modifyed :
 * @description :
 **/

/**
 * 모든 Redis 데이터 타입에 대한 공통 웹 요청(생성, 조회, 삭제, 존재 확인)을 처리하는 컨트롤러입니다.
 * RedisRequest DTO의 'type' 필드를 사용하여 적절한 서비스로 작업을 위임합니다.
 */
@RestController
@RequestMapping("/redis") // 최상위 /redis 경로를 사용하여 범용 엔드포인트를 제공
public class RedisCommonController {

    private final RedisDispatcherService redisDispatcherService;

    public RedisCommonController(RedisDispatcherService redisDispatcherService) {
        this.redisDispatcherService = redisDispatcherService;
    }

    /**
     * 지정된 타입에 따라 Redis에 데이터를 저장하고 만료를 처리합니다.
     * RedisRequest Body의 'type' 필드에 따라 STRING, LIST, SET, ZSET, HASH 서비스로 위임됩니다.
     * - STRING: 단일 값 저장
     * - LIST: 리스트의 오른쪽에 요소 추가 (RPUSH)
     * - SET: Set에 멤버 추가
     * - ZSET: Sorted Set에 멤버 (스코어 0.0) 또는 맵 형태로 여러 멤버/스코어 추가
     * - HASH: Hash에 맵 형태로 여러 필드/값 추가 (HMSET)
     *
     * @param request 타입, 키, 값, 만료 시간을 포함하는 RedisRequest 객체
     * @return 확인 메시지
     * @throws IllegalArgumentException 지원되지 않는 타입이거나 필수 필드가 누락된 경우
     */
    @PostMapping("/set")
    public String setData(@RequestBody RedisRequest request) {

        // 필수 필드 유효성 검사
        if (request.getType() == null || request.getKey() == null || request.getValue() == null) {
            throw new IllegalArgumentException("Error: Type, key, and value are required in the request body.");
        }

        Optional<RedisDataType> type = RedisDataType.fromString( request.getType() );
        String key = request.getKey();
        Object value = request.getValue();
        int expirationSeconds = request.getExpirationSeconds();

        if (expirationSeconds > 0) {
            redisDispatcherService.dispatchSetDataWithExpiration(type.orElseThrow().getServiceBeanName(), key, value, expirationSeconds);
            return "Data set with expiration (" + expirationSeconds + "s) successfully for type " + type.orElseThrow().name() + "!";
        } else {
            redisDispatcherService.dispatchSetData(type.orElseThrow().getServiceBeanName(), key, value);
            return "Data set successfully for type " + type.orElseThrow().name() + "!";
        }
    }

    /**
     * 지정된 타입에 따라 Redis에서 데이터를 가져옵니다.
     * URL 쿼리 파라미터로 타입과 키를 받습니다.
     * @param typeString Redis 데이터 타입 문자열 (예: "STRING", "LIST")
     * @param key Redis 키
     * @return 가져온 데이터 (String, List, Set, Map 등)
     * @throws IllegalArgumentException 지원되지 않는 타입인 경우
     */
    @GetMapping("/get")
    public Object getData(@RequestParam(value = "type") String typeString, @RequestParam(value = "key") String key) {
        RedisDataType type = RedisDataType.fromString(typeString)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Redis type: " + typeString));
        return redisDispatcherService.dispatchGetData(type.getServiceBeanName(), key);
    }

    /**
     * 지정된 타입에 따라 Redis에서 데이터를 삭제합니다.
     * URL 쿼리 파라미터로 타입과 키를 받습니다.
     * @param typeString Redis 데이터 타입 문자열
     * @param key Redis 키
     * @return 확인 메시지
     * @throws IllegalArgumentException 지원되지 않는 타입인 경우
     */
    @DeleteMapping("/delete")
    public String deleteData(@RequestParam(value = "type") String typeString, @RequestParam(value = "key") String key) {
        RedisDataType type = RedisDataType.fromString(typeString)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Redis type: " + typeString));

        if (redisDispatcherService.dispatchDeleteData(type.getServiceBeanName(), key)) {
            return "Data deleted successfully for type " + type.name() + "!";
        }
        return "Failed to delete data or key not found for type " + type.name() + ".";
    }

    /**
     * 지정된 타입에 따라 Redis에 키가 존재하는지 확인합니다.
     * URL 쿼리 파라미터로 타입과 키를 받습니다.
     * @param typeString Redis 데이터 타입 문자열
     * @param key Redis 키
     * @return 키 존재 여부
     * @throws IllegalArgumentException 지원되지 않는 타입인 경우
     */
    @GetMapping("/has")
    public boolean hasKey(@RequestParam(value = "type") String typeString, @RequestParam(value = "key") String key) {
        RedisDataType type = RedisDataType.fromString(typeString)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Redis type: " + typeString));
        return redisDispatcherService.dispatchHasKey(type.getServiceBeanName(), key);
    }
}