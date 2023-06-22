package com.karim.jwt.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

/**
 * @author : sblim
 * @version : 1.0.0
 * @package : com.karim.jwt.entity
 * @name : spring-basic-server
 * @date : 2023. 04. 26. 026 오후 2:52
 * @modifyed :
 * @description :
 **/
@Entity
@Table(name = "authority")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Authority {

    @Id
    @Column(name = "authority_name", length = 50)
    private String authorityName;
}
