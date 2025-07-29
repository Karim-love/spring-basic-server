package com.demo.redis_spring.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author : sblim
 * @version : 1.0.0
 * @package : com.demo.redis_spring.model
 * @name : spring-basic-server
 * @date : 2025. 07. 29. 화 오후 3:31
 * @modifyed :
 * @description :
 **/

/**
 * Redis 작업 요청을 위한 DTO
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RedisRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String type;             // Redis 데이터 타입 (예: "STRING", "LIST", "SET", "ZSET", "HASH")
    private String key;              // Redis 키
    private Object value;            // 저장할 값 (String, JSON 객체 등, 단일 값 또는 HASH/ZSET의 다중 값 Map)
    private int expirationSeconds;   // 만료 시간 (초, 0 이하는 만료 없음)
    private Object member;           // SET/ZSET 작업 시 단일 멤버
    private double score;            // ZSET 작업 시 스코어
    private double maxScore;         // ZSET 범위 조회 시 최대 스코어
    private double minScore;         // ZSET 범위 조회 시 최소 스코어
    private String field;            // HASH 작업 시 필드
}
