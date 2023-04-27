package com.karim.jwt.repository;

import com.karim.jwt.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author : sblim
 * @version : 1.0.0
 * @package : com.karim.jwt.repository
 * @name : spring-basic-server
 * @date : 2023. 04. 27. 027 오후 2:32
 * @modifyed :
 * @description :JpaRepository를 extends 하면 findAll. save 등의 메소드를 기본적으로 사용할 수 있음
 **/

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // 쿼리가 수행이 될 때 Lazy 조회가 아닌 Eager 조회로 authorities 정보를 같이 가져옴
    @EntityGraph(attributePaths = "authorities")
    // Username 기준으로 User 정보 및 권한 정보 get
    Optional<User> findOneWithAuthoritiesByUsername(String username);
}