package com.demo.redis_spring.service;

/**
 * @author : sblim
 * @version : 1.0.0
 * @package : com.demo.redis_spring.service
 * @name : spring-basic-server
 * @date : 2025. 07. 29. 화 오후 4:01
 * @modifyed :
 * @description :
 **/

/**
 * Redis 작업 공통 인터페이스. 각 구현체는 Redis 데이터 타입별(String, List 등) 작업을 처리합니다.
 */
public interface RedisOperationService {

    /**
     * Redis에 데이터를 저장합니다.
     * @param key 저장할 키
     * @param value 저장할 값
     */
    void setData(String key, Object value);

    /**
     * Redis에 데이터를 저장하고 만료 시간을 설정합니다.
     * @param key 저장할 키
     * @param value 저장할 값
     * @param timeoutSeconds 만료 시간 (초)
     */
    void setDataWithExpiration(String key, Object value, long timeoutSeconds);

    /**
     * Redis에서 데이터를 가져옵니다.
     * @param key 가져올 키
     * @return 저장된 값 또는 요소들
     */
    Object getData(String key);

    /**
     * Redis에서 데이터를 삭제합니다.
     * @param key 삭제할 키
     * @return 삭제 성공 여부
     */
    Boolean deleteData(String key);

    /**
     * 특정 키의 존재 여부를 확인합니다.
     * @param key 확인할 키
     * @return 존재 여부
     */
    Boolean hasKey(String key);
}
