package com.karim.jwt.dto;

import lombok.*;

/**
 * @author : sblim
 * @version : 1.0.0
 * @package : com.karim.jwt.dto
 * @name : spring-basic-server
 * @date : 2023. 04. 27. 027 오후 2:29
 * @modifyed :
 * @description : token 정보를 response 할 때 사용할 dto
 **/

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenDto {

    private String token;
}