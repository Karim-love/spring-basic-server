package com.demo.redis_spring.type.list.service;

import com.demo.redis_spring.service.RedisOperationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collections;
import java.util.List;


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
 * Redis List 타입 작업 서비스 구현체입니다. RedisOperationService 인터페이스를 구현합니다.
 */
@Service("listRedisService") // 디스패처에 주입하기 위한 특정 빈 이름
@Slf4j
public class RedisListServiceImpl implements RedisOperationService {

    private final ListOperations<String, Object> listOperations;

    public RedisListServiceImpl(RedisTemplate<String, Object> redisTemplate) {
        this.listOperations = redisTemplate.opsForList();
    }

    // --- RedisOperationService 인터페이스 List 타입 메서드 구현 ---

    /**
     * List 타입의 'setData'는 리스트의 오른쪽에 단일 요소를 추가하는 것으로 해석됩니다. (RPUSH)
     */
    @Override
    public void setData(String key, Object value) {
        Long length = listOperations.rightPush(key, value);
        log.info("[ListService] Added to list (right) via setData: {} -> {}. New length: {}", key, value, length);
    }

    /**
     * List 타입의 'setDataWithExpiration'은 리스트의 오른쪽에 단일 요소를 추가하고, 해당 키(리스트 전체)에 만료 시간을 설정합니다.
     */
    @Override
    public void setDataWithExpiration(String key, Object value, long timeoutSeconds) {
        listOperations.rightPush(key, value);
        listOperations.getOperations().expire(key, Duration.ofSeconds(timeoutSeconds)); // 리스트 키에 TTL 설정
        log.info("[ListService] Added to list (right) via setDataWithExpiration: {} -> {}. TTL set for key: {}s", key, value, timeoutSeconds);
    }

    /**
     * List 타입의 'getData'는 리스트의 모든 요소를 가져오는 것으로 해석됩니다. (LRANGE key 0 -1)
     * @param key 리스트의 키
     * @return 리스트의 모든 요소 (List<Object>)
     */
    @Override
    public List<Object> getData(String key) {
        List<Object> list = listOperations.range(key, 0, -1);
        log.info("[ListService] Retrieved all list elements: {} -> {}", key, list);
        return list != null ? list : Collections.emptyList();
    }

    @Override
    public Boolean deleteData(String key) {
        Boolean deleted = listOperations.getOperations().delete(key);
        log.info("[ListService] Delete data: {} -> {}", key, deleted);
        return deleted;
    }

    @Override
    public Boolean hasKey(String key) {
        Boolean exists = listOperations.getOperations().hasKey(key);
        log.info("[ListService] Has key: {} -> {}", key, exists);
        return exists;
    }

    // --- List 고유 메서드 (RedisOperationService 인터페이스에 속하지 않음) ---

    /**
     * 리스트의 오른쪽(꼬리)에 요소를 추가합니다. (Redis 명령어: RPUSH)
     * @param key 리스트의 키
     * @param value 추가할 요소
     * @return 리스트의 새로운 길이
     */
    public Long addToListRight(String key, Object value) {
        Long length = listOperations.rightPush(key, value);
        log.info("[ListService] Added to list (right): {} -> {}. New length: {}", key, value, length);
        return length;
    }

    /**
     * 리스트의 왼쪽(머리)에 요소를 추가합니다. (Redis 명령어: LPUSH)
     * @param key 리스트의 키
     * @param value 추가할 요소
     * @return 리스트의 새로운 길이
     */
    public Long addToListLeft(String key, Object value) {
        Long length = listOperations.leftPush(key, value);
        log.info("[ListService] Added to list (left): {} -> {}. New length: {}", key, value, length);
        return length;
    }

    /**
     * 리스트의 특정 범위에 있는 모든 요소를 가져옵니다. (Redis 명령어: LRANGE)
     * @param key 리스트의 키
     * @param start 시작 인덱스 (0부터 시작)
     * @param end 끝 인덱스 (-1은 마지막 요소)
     * @return 리스트 요소들 (List<Object>)
     */
    public List<Object> getListRange(String key, long start, long end) {
        List<Object> list = listOperations.range(key, start, end);
        log.info("[ListService] Retrieved list range: {} [{}-{}] -> {}", key, start, end, list);
        return list != null ? list : Collections.emptyList();
    }

    /**
     * 리스트의 특정 인덱스에 있는 요소를 가져옵니다. (Redis 명령어: LINDEX)
     * @param key 리스트의 키
     * @param index 인덱스
     * @return 해당 인덱스의 요소
     */
    public Object getListIndex(String key, long index) {
        Object value = listOperations.index(key, index);
        log.info("[ListService] Retrieved list index: {} [{}] -> {}", key, index, value);
        return value;
    }

    /**
     * 리스트의 길이를 가져옵니다. (Redis 명령어: LLEN)
     * @param key 리스트의 키
     * @return 리스트의 길이
     */
    public Long getListSize(String key) {
        Long size = listOperations.size(key);
        log.info("[ListService] List size for {}: {}", key, size);
        return size;
    }

    /**
     * 리스트의 왼쪽 끝에서 요소를 제거하고 반환합니다. (Redis 명령어: LPOP)
     * @param key 리스트의 키
     * @return 제거된 요소
     */
    public Object popListLeft(String key) {
        Object value = listOperations.leftPop(key);
        log.info("[ListService] Popped from list (left): {} -> {}", key, value);
        return value;
    }

    /**
     * 리스트의 오른쪽 끝에서 요소를 제거하고 반환합니다. (Redis 명령어: RPOP)
     * @param key 리스트의 키
     * @return 제거된 요소
     */
    public Object popListRight(String key) {
        Object value = listOperations.rightPop(key);
        log.info("[ListService] Popped from list (right): {} -> {}", key, value);
        return value;
    }
}