package com.demo.redis_spring.type.hash.service;

import com.demo.redis_spring.service.RedisOperationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations; // HashOperations 임포트
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collections;
import java.util.Map;

/**
 * @author : sblim
 * @version : 1.0.0
 * @package : com.demo.redis_spring.service
 * @name : spring-basic-server
 * @date : 2025. 07. 29. 화 오후 4:24
 * @modifyed :
 * @description :
 **/

/**
 * Redis Hash 타입 작업 서비스 구현체입니다. RedisOperationService 인터페이스를 구현합니다.
 */
@Service("hashRedisService") // 디스패처에 주입하기 위한 특정 빈 이름
@Slf4j
public class RedisHashServiceImpl implements RedisOperationService {

    private final HashOperations<String, String, Object> hashOperations; // HashOperations 사용

    public RedisHashServiceImpl(RedisTemplate<String, Object> redisTemplate) {
        // HashOperations는 Hash Key를 String으로, Hash Value를 Object로 사용하도록 지정
        this.hashOperations = redisTemplate.opsForHash();
    }

    // --- RedisOperationService 인터페이스 Hash 타입 메서드 구현 ---

    /**
     * Hash 타입의 'setData'는 Map<String, Object> 형태의 value를 받아 여러 필드-값 쌍을 Hash에 저장합니다. (HMSET/HSET)
     * value가 Map이 아니면 지원하지 않습니다.
     * @param key Hash의 키
     * @param value 저장할 필드-값 쌍 (Map<String, Object> 형태)
     */
    @Override
    public void setData(String key, Object value) {

        if (value instanceof Map) {
            Map<String, Object> entries = (Map<String, Object>) value;
            hashOperations.putAll(key, entries); // 여러 필드-값 쌍 저장
            log.info("[HashService] Stored multiple entries in Hash via setData: {} -> {}", key, entries);

        } else {
            log.error("[HashService] setData for HASH type requires a Map<String, Object> value. Received: {}", value.getClass().getName());
            throw new IllegalArgumentException("For HASH type, 'value' must be a Map<String, Object>.");
        }
    }

    /**
     * Hash 타입의 'setDataWithExpiration'은 Map<String, Object> 형태의 value를 받아 Hash에 저장하고, 해당 키(Hash 전체)에 만료 시간을 설정합니다.
     * @param key Hash의 키
     * @param value 저장할 필드-값 쌍
     * @param timeoutSeconds 만료 시간 (초)
     */
    @Override
    public void setDataWithExpiration(String key, Object value, long timeoutSeconds) {
        setData(key, value); // 필드-값 쌍 추가는 setData 로직 재사용
        hashOperations.getOperations().expire(key, Duration.ofSeconds(timeoutSeconds)); // Hash 키에 TTL 설정
        log.info("[HashService] Stored entries in Hash via setDataWithExpiration: {} -> {}. TTL set for key: {}s", key, value, timeoutSeconds);
    }

    /**
     * Hash 타입의 'getData'는 Hash의 모든 필드-값 쌍을 가져오는 것으로 해석됩니다. (HGETALL)
     * @param key Hash의 키
     * @return Hash의 모든 필드-값 쌍 (Map<String, Object>)
     */
    @Override
    public Map<String, Object> getData(String key) {
        Map<String, Object> entries = hashOperations.entries(key);
        log.info("[HashService] Retrieved all Hash entries: {} -> {}", key, entries);
        return entries != null ? entries : Collections.emptyMap();
    }

    @Override
    public Boolean deleteData(String key) {
        Boolean deleted = hashOperations.getOperations().delete(key);
        log.info("[HashService] Delete data: {} -> {}", key, deleted);
        return deleted;
    }

    @Override
    public Boolean hasKey(String key) {
        Boolean exists = hashOperations.getOperations().hasKey(key);
        log.info("[HashService] Has key: {} -> {}", key, exists);
        return exists;
    }

    // --- Hash 고유 메서드 (RedisOperationService 인터페이스에 속하지 않음) ---

    /**
     * Hash에 단일 필드-값을 저장합니다. (HSET)
     * @param key Hash의 키
     * @param field 필드 키
     * @param value 저장할 값
     */
    public void putHashEntry(String key, String field, Object value) {
        hashOperations.put(key, field, value);
        log.info("[HashService] Put entry into Hash: {} - {} -> {}", key, field, value);
    }

    /**
     * Hash에서 특정 필드의 값을 가져옵니다. (HGET)
     * @param key Hash의 키
     * @param field 가져올 필드 키
     * @return 해당 필드의 값
     */
    public Object getHashEntry(String key, String field) {
        Object value = hashOperations.get(key, field);
        log.info("[HashService] Retrieved Hash entry: {} - {} -> {}", key, field, value);
        return value;
    }

    /**
     * Hash의 모든 필드-값 쌍을 가져옵니다. (HGETALL)
     * @param key Hash의 키
     * @return Hash의 모든 필드-값 쌍 (Map<String, Object>)
     */
    public Map<String, Object> getAllHashEntries(String key) {
        Map<String, Object> entries = hashOperations.entries(key);
        log.info("[HashService] Retrieved all Hash entries: {} -> {}", key, entries);
        return entries != null ? entries : Collections.emptyMap();
    }

    /**
     * Hash에서 하나 이상의 필드를 제거합니다. (HDEL)
     * @param key Hash의 키
     * @param fields 제거할 필드들
     * @return 성공적으로 제거된 필드의 수
     */
    public Long deleteHashFields(String key, Object... fields) {
        Long deletedCount = hashOperations.delete(key, fields);
        log.info("[HashService] Deleted fields from Hash: {} - {} -> {}. Deleted count: {}", key, fields, deletedCount);
        return deletedCount;
    }

    /**
     * Hash에 특정 필드가 존재하는지 확인합니다. (HEXISTS)
     * @param key Hash의 키
     * @param field 확인할 필드 키
     * @return 필드가 Hash에 존재하면 true, 그렇지 않으면 false
     */
    public Boolean hasHashField(String key, String field) {
        Boolean exists = hashOperations.hasKey(key, field);
        log.info("[HashService] Has Hash field: {} - {} -> {}", key, field, exists);
        return exists;
    }

    /**
     * Hash의 필드 수를 가져옵니다. (HLEN)
     * @param key Hash의 키
     * @return Hash의 필드 수
     */
    public Long getHashSize(String key) {
        Long size = hashOperations.size(key);
        log.info("[HashService] Hash size for {}: {}", key, size);
        return size;
    }
}