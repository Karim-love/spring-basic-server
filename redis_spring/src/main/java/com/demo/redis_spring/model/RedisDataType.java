package com.demo.redis_spring.model;

import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

/**
 * @author : sblim
 * @version : 1.0.0
 * @package : com.demo.redis_spring.model
 * @name : spring-basic-server
 * @date : 2025. 07. 29. 화 오후 5:14
 * @modifyed :
 * @description :
 **/

/**
 * Redis 데이터 타입을 정의하는 Enum 클래스입니다.
 * 각 타입에 해당하는 서비스 빈 이름을 포함합니다.
 */
@Getter
public enum RedisDataType {

    STRING("stringRedisService"),
    LIST("listRedisService"),
    SET("setRedisService"),
    ZSET("zsetRedisService"),
    HASH("hashRedisService");

    /**
     * -- GETTER --
     *  해당 Redis 데이터 타입에 매핑되는 서비스 빈 이름을 반환합니다.
     *  예: STRING -> "stringRedisService"
     *
     * @return 서비스 빈 이름
     */
    private final String serviceBeanName;

    RedisDataType(String serviceBeanName) {
        this.serviceBeanName = serviceBeanName;
    }

    /**
     * 문자열 값을 해당하는 RedisDataType Enum으로 변환합니다.
     * 대소문자를 구분하지 않습니다.
     * @param typeString 변환할 문자열 (예: "STRING", "list", "HASH")
     * @return 해당하는 RedisDataType (찾지 못하면 Optional.empty())
     */
    public static Optional<RedisDataType> fromString(String typeString) {
        if (typeString == null || typeString.trim().isEmpty()) {
            return Optional.empty();
        }
        return Arrays.stream(RedisDataType.values())
                .filter(type -> type.name().equalsIgnoreCase(typeString))
                .findFirst();
    }
}