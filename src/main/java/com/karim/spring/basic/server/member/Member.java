package com.karim.spring.basic.server.member;

/**
 * @author : sblim
 * @version : 1.0.0
 * @package : com.karim.spring.basic.server.member
 * @name : spring-basic-server
 * @date : 2023. 02. 02. 002 오후 5:37
 * @modifyed :
 * @description : 회원 객체 정의
 **/

public class Member {

    private Long id;
    private String name;
    private Grade grade;

    public Member(Long id, String name, Grade grade) {
        this.id = id;
        this.name = name;
        this.grade = grade;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }
}
