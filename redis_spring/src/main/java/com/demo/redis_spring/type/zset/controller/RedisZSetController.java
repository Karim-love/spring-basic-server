package com.demo.redis_spring.type.zset.controller;

import com.demo.redis_spring.model.RedisRequest;
import com.demo.redis_spring.service.RedisDispatcherService;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * @author : sblim
 * @version : 1.0.0
 * @package : com.demo.redis_spring.zset.controller
 * @name : spring-basic-server
 * @date : 2025. 07. 29. 화 오후 4:43
 * @modifyed :
 * @description :
 **/


/**
 * Redis Sorted Set (ZSet) 타입 데이터에 대한 웹 요청을 처리하는 컨트롤러입니다.
 */
@RestController
@RequestMapping("/redis/zset")
public class RedisZSetController {

    private final RedisDispatcherService redisDispatcherService;

    public RedisZSetController(RedisDispatcherService redisDispatcherService) {
        this.redisDispatcherService = redisDispatcherService;
    }

    /**
     * Sorted Set에 단일 멤버와 스코어를 추가합니다. 요청 바디에 RedisRequest 객체를 받습니다.
     * @param request 키, 멤버, 스코어를 포함하는 RedisRequest 객체
     * @return 확인 메시지
     */
    @PostMapping("/add")
    public String addMemberToZSet(@RequestBody RedisRequest request) {
        if (request.getKey() == null || request.getMember() == null) { // member 필드 사용
            return "Error: Key and member are required for adding to ZSet.";
        }
        redisDispatcherService.addMemberToZSetWithScore(request.getKey(), request.getMember(), request.getScore()); // score 필드 사용
        return "Added member to ZSet successfully!";
    }

    /**
     * Sorted Set의 모든 멤버와 스코어를 가져옵니다. URL 쿼리 파라미터로 키를 받습니다.
     * @param key Sorted Set의 키
     * @return Sorted Set의 모든 멤버와 스코어 (Set<TypedTuple<Object>>)
     */
    @GetMapping("/membersWithScores")
    public Set<TypedTuple<Object>> getZSetMembersWithScores(@RequestParam(value = "key") String key) {
        return redisDispatcherService.getZSetMembersWithScores(key);
    }

    /**
     * 지정된 스코어 범위 내의 모든 멤버를 가져옵니다. URL 쿼리 파라미터로 키, 최소 스코어, 최대 스코어를 받습니다.
     * @param key Sorted Set의 키
     * @param minScore 최소 스코어
     * @param maxScore 최대 스코어
     * @return 스코어 범위 내의 멤버들 (Set<Object>)
     */
    @GetMapping("/membersByScoreRange")
    public Set<Object> getZSetMembersByScoreRange(
            @RequestParam(value = "key") String key,
            @RequestParam(value = "minScore") double minScore, // minScore 필드 사용
            @RequestParam(value = "maxScore") double maxScore // maxScore 필드 사용
    ) {
        return redisDispatcherService.getZSetMembersByScoreRange(key, minScore, maxScore);
    }

    /**
     * 멤버의 스코어를 가져옵니다. URL 쿼리 파라미터로 키와 멤버를 받습니다.
     * @param key Sorted Set의 키
     * @param member 스코어를 조회할 멤버
     * @return 멤버의 스코어, 멤버가 없으면 null
     */
    @GetMapping("/memberScore")
    public Double getZSetMemberScore(
            @RequestParam(value = "key") String key,
            @RequestParam(value = "member") String member // member 필드 사용
    ) {
        return redisDispatcherService.getZSetMemberScore(key, member);
    }

    /**
     * Sorted Set에서 하나 이상의 멤버를 제거합니다. 요청 바디에 RedisRequest 객체를 받습니다.
     * @param request 키와 제거할 멤버를 포함하는 RedisRequest 객체.
     * @return 확인 메시지
     */
    @DeleteMapping("/remove")
    public String removeMembersFromZSet(@RequestBody RedisRequest request) {
        if (request.getKey() == null || request.getMember() == null) { // member 필드 사용
            return "Error: Key and member are required for removing from ZSet.";
        }
        redisDispatcherService.removeMembersFromZSet(request.getKey(), request.getMember());
        return "Removed member from ZSet successfully!";
    }

    /**
     * Sorted Set의 멤버 수를 가져옵니다. URL 쿼리 파라미터로 키를 받습니다.
     * @param key Sorted Set의 키
     * @return Sorted Set의 멤버 수
     */
    @GetMapping("/size")
    public Long getZSetSize(@RequestParam(value = "key") String key) {
        return redisDispatcherService.getZSetSize(key);
    }
}