package com.demo.redis_spring.type.set.service;

/**
 * @author : sblim
 * @version : 1.0.0
 * @package : com.demo.redis_spring.service
 * @name : spring-basic-server
 * @date : 2025. 07. 29. 화 오후 4:19
 * @modifyed :
 * @description :
 **/

import com.demo.redis_spring.service.RedisOperationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collections;
import java.util.Set;

/**
 * Redis Set 타입 작업 서비스 구현체입니다. RedisOperationService 인터페이스를 구현합니다.
 */
@Service("setRedisService") // 디스패처에 주입하기 위한 특정 빈 이름
@Slf4j
public class RedisSetServiceImpl implements RedisOperationService {

    private final SetOperations<String, Object> setOperations;

    public RedisSetServiceImpl(RedisTemplate<String, Object> redisTemplate) {
        this.setOperations = redisTemplate.opsForSet();
    }

    // --- RedisOperationService 인터페이스 Set 타입 메서드 구현 ---

    /**
     * Set 타입의 'setData'는 Set에 단일 멤버를 추가하는 것으로 해석됩니다. (SADD)
     * @param key Set의 키
     * @param value 추가할 멤버
     */
    @Override
    public void setData(String key, Object value) {
        Long addedCount = setOperations.add(key, value);
        log.info("[SetService] Added to set via setData: {} -> {}. Added count: {}", key, value, addedCount);
    }

    /**
     * Set 타입의 'setDataWithExpiration'은 Set에 단일 멤버를 추가하고, 해당 키(Set 전체)에 만료 시간을 설정합니다.
     * @param key Set의 키
     * @param value 추가할 멤버
     * @param timeoutSeconds 만료 시간 (초)
     */
    @Override
    public void setDataWithExpiration(String key, Object value, long timeoutSeconds) {
        setOperations.add(key, value);
        setOperations.getOperations().expire(key, Duration.ofSeconds(timeoutSeconds)); // Set 키에 TTL 설정
        log.info("[SetService] Added to set via setDataWithExpiration: {} -> {}. TTL set for key: {}s", key, value, timeoutSeconds);
    }

    /**
     * Set 타입의 'getData'는 Set의 모든 멤버를 가져오는 것으로 해석됩니다. (SMEMBERS)
     * @param key Set의 키
     * @return Set의 모든 멤버 (Set<Object>)
     */
    @Override
    public Set<Object> getData(String key) {
        Set<Object> members = setOperations.members(key);
        log.info("[SetService] Retrieved all set members: {} -> {}", key, members);
        return members != null ? members : Collections.emptySet();
    }

    @Override
    public Boolean deleteData(String key) {
        Boolean deleted = setOperations.getOperations().delete(key);
        log.info("[SetService] Delete data: {} -> {}", key, deleted);
        return deleted;
    }

    @Override
    public Boolean hasKey(String key) {
        Boolean exists = setOperations.getOperations().hasKey(key);
        log.info("[SetService] Has key: {} -> {}", key, exists);
        return exists;
    }

    // --- Set 고유 메서드 (RedisOperationService 인터페이스에 속하지 않음) ---

    /**
     * Set에 하나 이상의 멤버를 추가합니다. (Redis 명령어: SADD)
     * @param key Set의 키
     * @param members 추가할 멤버들
     * @return 성공적으로 추가된 새 멤버의 수
     */
    public Long addMembersToSet(String key, Object... members) {
        Long addedCount = setOperations.add(key, members);
        log.info("[SetService] Added members to set: {} -> {}. Added count: {}", key, members, addedCount);
        return addedCount;
    }

    /**
     * Set에서 하나 이상의 멤버를 제거합니다. (Redis 명령어: SREM)
     * @param key Set의 키
     * @param members 제거할 멤버들
     * @return 성공적으로 제거된 멤버의 수
     */
    public Long removeMembersFromSet(String key, Object... members) {
        Long removedCount = setOperations.remove(key, members);
        log.info("[SetService] Removed members from set: {} -> {}. Removed count: {}", key, members, removedCount);
        return removedCount;
    }

    /**
     * 멤버가 Set의 멤버인지 확인합니다. (Redis 명령어: SISMEMBER)
     * @param key Set의 키
     * @param member 확인할 멤버
     * @return 멤버가 Set에 존재하면 true, 그렇지 않으면 false
     */
    public Boolean isMemberOfSet(String key, Object member) {
        Boolean isMember = setOperations.isMember(key, member);
        log.info("[SetService] Is member of set: {} - {} -> {}", key, member, isMember);
        return isMember;
    }

    /**
     * Set의 모든 멤버를 가져옵니다. (Redis 명령어: SMEMBERS)
     * @param key Set의 키
     * @return Set의 모든 멤버 (Set<Object>)
     */
    public Set<Object> getSetMembers(String key) {
        Set<Object> members = setOperations.members(key);
        log.info("[SetService] Retrieved set members: {} -> {}", key, members);
        return members != null ? members : Collections.emptySet();
    }

    /**
     * Set의 멤버 수를 가져옵니다. (Redis 명령어: SCARD)
     * @param key Set의 키
     * @return Set의 멤버 수
     */
    public Long getSetSize(String key) {
        Long size = setOperations.size(key);
        log.info("[SetService] Set size for {}: {}", key, size);
        return size;
    }
}