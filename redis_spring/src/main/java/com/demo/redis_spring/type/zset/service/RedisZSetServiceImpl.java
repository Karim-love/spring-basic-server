package com.demo.redis_spring.type.zset.service;

import com.demo.redis_spring.service.RedisOperationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations; // ZSetOperations 임포트
import org.springframework.data.redis.core.ZSetOperations.TypedTuple; // TypedTuple 임포트
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author : sblim
 * @version : 1.0.0
 * @package : com.demo.redis_spring.service
 * @name : spring-basic-server
 * @date : 2025. 07. 29. 화 오후 4:22
 * @modifyed :
 * @description :
 **/

/**
 * Redis Sorted Set 타입 작업 서비스 구현체입니다. RedisOperationService 인터페이스를 구현합니다.
 */
@Service("zsetRedisService") // 디스패처에 주입하기 위한 특정 빈 이름
@Slf4j
public class RedisZSetServiceImpl implements RedisOperationService {

    private final ZSetOperations<String, Object> zSetOperations;

    public RedisZSetServiceImpl(RedisTemplate<String, Object> redisTemplate) {
        this.zSetOperations = redisTemplate.opsForZSet();
    }

    // --- RedisOperationService 인터페이스 Sorted Set 타입 메서드 구현 ---

    /**
     * Sorted Set 타입의 'setData'는 멤버와 스코어를 추가하는 것으로 해석됩니다. (ZADD)
     * value가 Map<String, Double>이면 여러 멤버/스코어를 추가하고, 아니면 단일 멤버/기본 스코어 0.0으로 추가합니다.
     * @param key Sorted Set의 키
     * @param value 추가할 멤버(들). 단일 Object 또는 Map<String, Double> 형태.
     */
    @Override
    public void setData(String key, Object value) {

        if (value instanceof Map) {
            // value가 Map 형태일 경우, 여러 멤버와 스코어를 추가
            Map<String, Double> membersWithScores = (Map<String, Double>) value;
            Set<TypedTuple<Object>> tuples = membersWithScores.entrySet().stream()
                    .map(entry -> TypedTuple.of((Object) entry.getKey(), entry.getValue()))
                    .collect(Collectors.toSet());
            Long addedCount = zSetOperations.add(key, tuples);
            log.info("[ZSetService] Added multiple members to ZSet via setData: {} -> {}. Added count: {}", key, membersWithScores, addedCount);
        } else {
            // value가 단일 멤버일 경우, 기본 스코어 0.0으로 추가
            Boolean added = zSetOperations.add(key, value, 0.0); // 기본 스코어 0.0
            log.info("[ZSetService] Added single member to ZSet via setData: {} -> {}. Added: {}", key, value, added);
        }
    }

    /**
     * Sorted Set 타입의 'setDataWithExpiration'은 멤버와 스코어를 추가하고, 해당 키(Set 전체)에 만료 시간을 설정합니다.
     * @param key Sorted Set의 키
     * @param value 추가할 멤버(들)
     * @param timeoutSeconds 만료 시간 (초)
     */
    @Override
    public void setDataWithExpiration(String key, Object value, long timeoutSeconds) {
        setData(key, value); // 멤버 추가는 setData 로직 재사용
        zSetOperations.getOperations().expire(key, Duration.ofSeconds(timeoutSeconds)); // ZSet 키에 TTL 설정
        log.info("[ZSetService] Added to ZSet via setDataWithExpiration: {} -> {}. TTL set for key: {}s", key, value, timeoutSeconds);
    }

    /**
     * Sorted Set 타입의 'getData'는 Set의 모든 멤버와 스코어를 가져오는 것으로 해석됩니다. (ZRANGE WITHSCORES)
     * @param key Sorted Set의 키
     * @return Set의 모든 멤버와 스코어 (Set<TypedTuple<Object>>)
     */
    @Override
    public Set<TypedTuple<Object>> getData(String key) {
        Set<TypedTuple<Object>> tuples = zSetOperations.rangeWithScores(key, 0, -1);
        log.info("[ZSetService] Retrieved all ZSet members with scores: {} -> {}", key, tuples);
        return tuples != null ? tuples : Collections.emptySet();
    }

    @Override
    public Boolean deleteData(String key) {
        Boolean deleted = zSetOperations.getOperations().delete(key);
        log.info("[ZSetService] Delete data: {} -> {}", key, deleted);
        return deleted;
    }

    @Override
    public Boolean hasKey(String key) {
        Boolean exists = zSetOperations.getOperations().hasKey(key);
        log.info("[ZSetService] Has key: {} -> {}", key, exists);
        return exists;
    }

    // --- Sorted Set 고유 메서드 (RedisOperationService 인터페이스에 속하지 않음) ---

    /**
     * Sorted Set에 단일 멤버와 스코어를 추가합니다. (ZADD)
     * @param key Sorted Set의 키
     * @param member 추가할 멤버
     * @param score 멤버의 스코어
     * @return 성공적으로 추가되었으면 true, 업데이트되었으면 false
     */
    public Boolean addMemberWithScore(String key, Object member, double score) {
        Boolean added = zSetOperations.add(key, member, score);
        log.info("[ZSetService] Added member to ZSet with score: {} -> {} ({}). Added: {}", key, member, score, added);
        return added;
    }

    /**
     * 지정된 스코어 범위 내의 모든 멤버를 가져옵니다. (ZRANGEBYSCORE)
     * @param key Sorted Set의 키
     * @param minScore 최소 스코어
     * @param maxScore 최대 스코어
     * @return 스코어 범위 내의 멤버들 (Set<Object>)
     */
    public Set<Object> getMembersByScoreRange(String key, double minScore, double maxScore) {
        Set<Object> members = zSetOperations.rangeByScore(key, minScore, maxScore);
        log.info("[ZSetService] Retrieved ZSet members by score range {}-{}: {} -> {}", minScore, maxScore, key, members);
        return members != null ? members : Collections.emptySet();
    }

    /**
     * 지정된 스코어 범위 내의 모든 멤버와 스코어를 가져옵니다. (ZRANGEBYSCORE WITHSCORES)
     * @param key Sorted Set의 키
     * @param minScore 최소 스코어
     * @param doubleMaxScore 최대 스코어
     * @return 스코어 범위 내의 멤버와 스코어 (Set<TypedTuple<Object>>)
     */
    public Set<TypedTuple<Object>> getMembersWithScoresByScoreRange(String key, double minScore, double doubleMaxScore) {
        Set<TypedTuple<Object>> tuples = zSetOperations.rangeByScoreWithScores(key, minScore, doubleMaxScore);
        log.info("[ZSetService] Retrieved ZSet members with scores by score range {}-{}: {} -> {}", minScore, doubleMaxScore, key, tuples);
        return tuples != null ? tuples : Collections.emptySet();
    }

    /**
     * 멤버의 스코어를 가져옵니다. (ZSCORE)
     * @param key Sorted Set의 키
     * @param member 스코어를 조회할 멤버
     * @return 멤버의 스코어, 멤버가 없으면 null
     */
    public Double getMemberScore(String key, Object member) {
        Double score = zSetOperations.score(key, member);
        log.info("[ZSetService] Retrieved member score for {}: {} -> {}", key, member, score);
        return score;
    }

    /**
     * Sorted Set에서 하나 이상의 멤버를 제거합니다. (ZREM)
     * @param key Sorted Set의 키
     * @param members 제거할 멤버들
     * @return 성공적으로 제거된 멤버의 수
     */
    public Long removeMembersFromZSet(String key, Object... members) {
        Long removedCount = zSetOperations.remove(key, members);
        log.info("[ZSetService] Removed members from ZSet: {} -> {}. Removed count: {}", key, members, removedCount);
        return removedCount;
    }

    /**
     * Sorted Set의 멤버 수를 가져옵니다. (ZCARD)
     * @param key Sorted Set의 키
     * @return Set의 멤버 수
     */
    public Long getZSetSize(String key) {
        Long size = zSetOperations.size(key);
        log.info("[ZSetService] ZSet size for {}: {}", key, size);
        return size;
    }

    /**
     * 지정된 스코어 범위 내의 멤버 수를 가져옵니다. (ZCOUNT)
     * @param key Sorted Set의 키
     * @param minScore 최소 스코어
     * @param maxScore 최대 스코어
     * @return 스코어 범위 내의 멤버 수
     */
    public Long countMembersInScoreRange(String key, double minScore, double maxScore) {
        Long count = zSetOperations.count(key, minScore, maxScore);
        log.info("[ZSetService] ZSet members count in score range {}-{}: {} -> {}", minScore, maxScore, key, count);
        return count;
    }
}