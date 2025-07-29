package com.demo.redis_spring.type.string.service;

import com.demo.redis_spring.service.RedisOperationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;


/**
* @package     : com.demo.redis_spring.service
* @name        : spring-basic-server
* @date        : 2025. 07. 29. 화 오후 4:08
* @author      : sblim
* @version     : 1.0.0
* @modifyed    :
* @description :
**/

/**
 * Service implementation for Redis String (Key-Value) operations.
 * Implements RedisOperationService interface.
 */
@Service("stringRedisService") // Give a specific name for injection into the dispatcher
@Slf4j
public class RedisStringServiceImpl implements RedisOperationService {

    private final ValueOperations<String, Object> valueOperations;

    public RedisStringServiceImpl(RedisTemplate<String, Object> redisTemplate) {
        this.valueOperations = redisTemplate.opsForValue();
    }

    @Override
    public void setData(String key, Object value) {
        valueOperations.set(key, value);
        log.info("[StringService] Set data: {} -> {}", key, value);
    }

    @Override
    public void setDataWithExpiration(String key, Object value, long timeoutSeconds) {
        valueOperations.set(key, value, Duration.ofSeconds(timeoutSeconds));
        log.info("[StringService] Set data with expiration: {} -> {} (TTL: {}s)", key, value, timeoutSeconds);
    }

    @Override
    public Object getData(String key) {
        Object value = valueOperations.get(key);
        log.info("[StringService] Get data: {} -> {}", key, value);
        return value;
    }

    @Override
    public Boolean deleteData(String key) {
        Boolean deleted = valueOperations.getOperations().delete(key);
        log.info("[StringService] Delete data: {} -> {}", key, deleted);
        return deleted;
    }

    @Override
    public Boolean hasKey(String key) {
        Boolean exists = valueOperations.getOperations().hasKey(key);
        log.info("[StringService] Has key: {} -> {}", key, exists);
        return exists;
    }
}