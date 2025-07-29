package com.demo.redis_spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author : sblim
 * @version : 1.0.0
 * @package : com.demo.redis_spring.config
 * @name : spring-basic-server
 * @date : 2025. 07. 29. 화 오후 2:32
 * @modifyed :
 * @description :
 **/

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {

        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);

        // Key 직렬화 (String)
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        // Value 직렬화 (JSON)
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());

        // Hash Key 직렬화 (String)
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        // Hash Value 직렬화 (JSON)
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());

        redisTemplate.afterPropertiesSet(); // 직렬화 설정을 적용

        return redisTemplate;
    }
}