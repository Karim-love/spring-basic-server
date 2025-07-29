package com.demo.redis_spring.type.set.controller;

import com.demo.redis_spring.model.RedisRequest;
import com.demo.redis_spring.service.RedisDispatcherService;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * @author : sblim
 * @version : 1.0.0
 * @package : com.demo.redis_spring.set.controller
 * @name : spring-basic-server
 * @date : 2025. 07. 29. 화 오후 5:08
 * @modifyed :
 * @description :
 **/

/**
 * Redis Set 타입 데이터에 대한 웹 요청을 처리하는 컨트롤러입니다.
 */
@RestController
@RequestMapping("/redis/set")
public class RedisSetController {

    private final RedisDispatcherService redisDispatcherService;

    public RedisSetController(RedisDispatcherService redisDispatcherService) {
        this.redisDispatcherService = redisDispatcherService;
    }

    /**
     * Set에 하나 이상의 멤버를 추가합니다. 요청 바디에 RedisRequest 객체를 받습니다.
     * @param request 키와 멤버를 포함하는 RedisRequest 객체. 값은 단일 멤버.
     * @return 확인 메시지
     */
    @PostMapping("/add")
    public String addMemberToSet(@RequestBody RedisRequest request) {
        if (request.getKey() == null || request.getMember() == null) { // member 필드 사용
            return "Error: Key and member are required for adding to set.";
        }
        redisDispatcherService.addMembersToSet(request.getKey(), request.getMember());
        return "Added member to set successfully!";
    }

    /**
     * Set에서 하나 이상의 멤버를 제거합니다. 요청 바디에 RedisRequest 객체를 받습니다.
     * @param request 키와 제거할 멤버를 포함하는 RedisRequest 객체.
     * @return 확인 메시지
     */
    @DeleteMapping("/remove")
    public String removeMemberFromSet(@RequestBody RedisRequest request) {
        if (request.getKey() == null || request.getMember() == null) { // member 필드 사용
            return "Error: Key and member are required for removing from set.";
        }
        redisDispatcherService.removeMembersFromSet(request.getKey(), request.getMember());
        return "Removed member from set successfully!";
    }

    /**
     * Set의 모든 멤버를 가져옵니다. URL 쿼리 파라미터로 키를 받습니다.
     * @param key Set의 키
     * @return Set의 모든 멤버 (Set<Object>)
     */
    @GetMapping("/members")
    public Set<Object> getSetMembers(@RequestParam(value = "key") String key) {
        return redisDispatcherService.getSetMembers(key);
    }

    /**
     * 멤버가 Set의 멤버인지 확인합니다. URL 쿼리 파라미터로 키와 멤버를 받습니다.
     * @param key Set의 키
     * @param member 확인할 멤버
     * @return 멤버 존재 여부
     */
    @GetMapping("/isMember")
    public boolean isMemberOfSet(@RequestParam(value = "key") String key, @RequestParam(value = "member") String member) {
        return redisDispatcherService.isMemberOfSet(key, member);
    }

    /**
     * Set의 멤버 수를 가져옵니다. URL 쿼리 파라미터로 키를 받습니다.
     * @param key Set의 키
     * @return Set의 멤버 수
     */
    @GetMapping("/size")
    public Long getSetSize(@RequestParam(value = "key") String key) {
        return redisDispatcherService.getSetSize(key);
    }
}