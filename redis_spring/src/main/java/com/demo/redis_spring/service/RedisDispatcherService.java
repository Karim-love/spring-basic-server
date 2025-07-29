package com.demo.redis_spring.service;

import com.demo.redis_spring.type.hash.service.RedisHashServiceImpl;
import com.demo.redis_spring.type.list.service.RedisListServiceImpl;
import com.demo.redis_spring.type.set.service.RedisSetServiceImpl;
import com.demo.redis_spring.type.zset.service.RedisZSetServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set; // Set 타입 임포트
import org.springframework.data.redis.core.ZSetOperations.TypedTuple; // TypedTuple 임포트

/**
 * @author : sblim
 * @version : 1.0.0
 * @package : com.demo.redis_spring.service
 * @name : spring-basic-server
 * @date : 2025. 07. 29. 화 오후 4:09
 * @modifyed :
 * @description :
 **/

/**
 * 제공된 타입에 따라 Redis 작업을 특정 RedisOperationService 구현체로 위임하는 디스패처 서비스입니다.
 */
@Service
@Slf4j
public class RedisDispatcherService {

    // Spring이 RedisOperationService를 구현하는 모든 빈을 맵으로 자동 주입합니다. (빈 이름이 키)
    private final Map<String, RedisOperationService> redisServiceMap;
    // 각 특정 Redis 서비스의 고유 메서드를 호출하기 위해 명시적으로 주입합니다.
    private final RedisListServiceImpl redisListService;
    private final RedisSetServiceImpl redisSetService; // SetService 추가
    private final RedisZSetServiceImpl redisZSetService; // ZSetService 추가
    private final RedisHashServiceImpl redisHashService; // HashService 추가

    public RedisDispatcherService( Map<String, RedisOperationService> redisServiceMap,
                                   RedisListServiceImpl redisListService,
                                   RedisSetServiceImpl redisSetService, // Set 서비스 빈 주입
                                   RedisZSetServiceImpl redisZSetService, // ZSet 서비스 빈 주입
                                   RedisHashServiceImpl redisHashService) {
        this.redisServiceMap = redisServiceMap;
        this.redisListService = redisListService;
        this.redisSetService = redisSetService; // 구체적인 Set 서비스 할당
        this.redisZSetService = redisZSetService; // 구체적인 ZSet 서비스 할당
        this.redisHashService = redisHashService; // 구체적인 Hash 서비스 할당
        log.info("RedisDispatcherService initialized with services: {}", redisServiceMap.keySet());
    }

    /**
     * 타입에 따라 setData 작업을 적절한 Redis 서비스로 위임합니다.
     * @param type Redis 작업 타입 ("STRING", "LIST", "SET", "ZSET", "HASH")
     * @param key 저장할 키
     * @param value 저장할 값
     * @throws IllegalArgumentException 지원되지 않는 타입인 경우
     */
    public void dispatchSetData(String type, String key, Object value) {
        RedisOperationService service = getService(type);
        log.debug("Dispatching setData for type: {}", type);
        service.setData(key, value);
    }

    /**
     * 타입에 따라 setDataWithExpiration 작업을 적절한 Redis 서비스로 위임합니다.
     * @param type Redis 작업 타입 ("STRING", "LIST", "SET", "ZSET", "HASH")
     * @param key 저장할 키
     * @param value 저장할 값
     * @param timeoutSeconds 만료 시간 (초)
     * @throws IllegalArgumentException 지원되지 않는 타입인 경우
     */
    public void dispatchSetDataWithExpiration(String type, String key, Object value, long timeoutSeconds) {
        RedisOperationService service = getService(type);
        log.debug("Dispatching setDataWithExpiration for type: {}", type);
        service.setDataWithExpiration(key, value, timeoutSeconds);
    }

    /**
     * 타입에 따라 getData 작업을 적절한 Redis 서비스로 위임합니다.
     * @param type Redis 작업 타입 ("STRING", "LIST", "SET", "ZSET", "HASH")
     * @param key 가져올 키
     * @return 저장된 값 또는 요소들
     * @throws IllegalArgumentException 지원되지 않는 타입인 경우
     */
    public Object dispatchGetData(String type, String key) {
        RedisOperationService service = getService(type);
        log.debug("Dispatching getData for type: {}", type);
        return service.getData(key);
    }

    /**
     * 타입에 따라 deleteData 작업을 적절한 Redis 서비스로 위임합니다.
     * @param type Redis 작업 타입 ("STRING", "LIST", "SET", "ZSET", "HASH")
     * @param key 삭제할 키
     * @return 삭제 성공 여부
     * @throws IllegalArgumentException 지원되지 않는 타입인 경우
     */
    public Boolean dispatchDeleteData(String type, String key) {
        RedisOperationService service = getService(type);
        log.debug("Dispatching deleteData for type: {}", type);
        return service.deleteData(key);
    }

    /**
     * 타입에 따라 hasKey 작업을 적절한 Redis 서비스로 위임합니다.
     * @param type Redis 작업 타입 ("STRING", "LIST", "SET", "ZSET", "HASH")
     * @param key 확인할 키
     * @return 키 존재 여부
     * @throws IllegalArgumentException 지원되지 않는 타입인 경우
     */
    public Boolean dispatchHasKey(String type, String key) {
        RedisOperationService service = getService(type);
        log.debug("Dispatching hasKey for type: {}", type);
        return service.hasKey(key);
    }

    // --- List 고유 메서드 직접 접근 ---
    // 이 메서드들은 공통 인터페이스에 속하지 않아, RedisListServiceImpl에서 직접 호출합니다.

    public Long addToListRight(String key, Object value) {
        log.debug("Direct call to addToListRight for key: {}", key);
        return redisListService.addToListRight(key, value);
    }

    public Long addToListLeft(String key, Object value) {
        log.debug("Direct call to addToListLeft for key: {}", key);
        return redisListService.addToListLeft(key, value);
    }

    public List<Object> getListRange(String key, long start, long end) {
        log.debug("Direct call to getListRange for key: {} ({} - {})", key, start, end);
        return redisListService.getListRange(key, start, end);
    }

    public Long getListSize(String key) {
        log.debug("Direct call to getListSize for key: {}", key);
        return redisListService.getListSize(key);
    }

    public Object popListLeft(String key) {
        log.debug("Direct call to popListLeft for key: {}", key);
        return redisListService.popListLeft(key);
    }

    public Object popListRight(String key) {
        log.debug("Direct call to popListRight for key: {}", key);
        return redisListService.popListRight(key);
    }


    // --- Set 고유 메서드 직접 접근 ---
    // 이 메서드들은 공통 인터페이스에 속하지 않아, RedisSetServiceImpl에서 직접 호출합니다.

    public Long addMembersToSet(String key, Object... members) {
        log.debug("Direct call to addMembersToSet for key: {}", key);
        return redisSetService.addMembersToSet(key, members);
    }

    public Set<Object> getSetMembers(String key) {
        log.debug("Direct call to getSetMembers for key: {}", key);
        return redisSetService.getSetMembers(key);
    }

    public Long removeMembersFromSet(String key, Object... members) {
        log.debug("Direct call to removeMembersFromSet for key: {}", key);
        return redisSetService.removeMembersFromSet(key, members);
    }

    public Boolean isMemberOfSet(String key, Object member) {
        log.debug("Direct call to isMemberOfSet for key: {} - member: {}", key, member);
        return redisSetService.isMemberOfSet(key, member);
    }

    public Long getSetSize(String key) {
        log.debug("Direct call to getSetSize for key: {}", key);
        return redisSetService.getSetSize(key);
    }

    // --- Sorted Set (ZSet) 고유 메서드 직접 접근 ---
    // 이 메서드들은 공통 인터페이스에 속하지 않아, RedisZSetServiceImpl에서 직접 호출합니다.

    public Boolean addMemberToZSetWithScore(String key, Object member, double score) {
        log.debug("Direct call to addMemberToZSetWithScore for key: {} - member: {} - score: {}", key, member, score);
        return redisZSetService.addMemberWithScore(key, member, score);
    }

    public Set<TypedTuple<Object>> getZSetMembersWithScores(String key) {
        log.debug("Direct call to getZSetMembersWithScores for key: {}", key);
        return redisZSetService.getData(key); // getData가 ZSet에서는 TypedTuple<Object>를 반환
    }

    public Set<Object> getZSetMembersByScoreRange(String key, double minScore, double maxScore) {
        log.debug("Direct call to getZSetMembersByScoreRange for key: {} ({} - {})", key, minScore, maxScore);
        return redisZSetService.getMembersByScoreRange(key, minScore, maxScore);
    }

    public Double getZSetMemberScore(String key, Object member) {
        log.debug("Direct call to getZSetMemberScore for key: {} - member: {}", key, member);
        return redisZSetService.getMemberScore(key, member);
    }

    public Long removeMembersFromZSet(String key, Object... members) {
        log.debug("Direct call to removeMembersFromZSet for key: {}", key);
        return redisZSetService.removeMembersFromZSet(key, members);
    }

    public Long getZSetSize(String key) {
        log.debug("Direct call to getZSetSize for key: {}", key);
        return redisZSetService.getZSetSize(key);
    }

    // --- Hash 고유 메서드 직접 접근 ---
    // 이 메서드들은 공통 인터페이스에 속하지 않아, RedisHashServiceImpl에서 직접 호출합니다.

    public void putHashEntry(String key, String field, Object value) {
        log.debug("Direct call to putHashEntry for key: {} - field: {} - value: {}", key, field, value);
        redisHashService.putHashEntry(key, field, value);
    }

    public Object getHashEntry(String key, String field) {
        log.debug("Direct call to getHashEntry for key: {} - field: {}", key, field);
        return redisHashService.getHashEntry(key, field);
    }

    public Map<String, Object> getAllHashEntries(String key) {
        log.debug("Direct call to getAllHashEntries for key: {}", key);
        return redisHashService.getAllHashEntries(key);
    }

    public Long deleteHashFields(String key, Object... fields) {
        log.debug("Direct call to deleteHashFields for key: {} - fields: {}", key, fields);
        return redisHashService.deleteHashFields(key, fields);
    }

    public Boolean hasHashField(String key, String field) {
        log.debug("Direct call to hasHashField for key: {} - field: {}", key, field);
        return redisHashService.hasHashField(key, field);
    }

    public Long getHashSize(String key) {
        log.debug("Direct call to getHashSize for key: {}", key);
        return redisHashService.getHashSize(key);
    }

    /**
     * 올바른 RedisOperationService 구현체를 가져오는 헬퍼 메서드입니다.
     * @param type 타입 문자열 ("STRING", "LIST", "SET", "ZSET", "HASH")
     * @return 해당 RedisOperationService
     * @throws IllegalArgumentException 지원되지 않는 타입인 경우
     */
    private RedisOperationService getService(String type) {

        RedisOperationService service = redisServiceMap.get(type);
        if (service == null) {
            log.error("Unsupported Redis type requested: {}", type);
            throw new IllegalArgumentException("Unsupported Redis type: " + type);
        }
        return service;
    }
}
