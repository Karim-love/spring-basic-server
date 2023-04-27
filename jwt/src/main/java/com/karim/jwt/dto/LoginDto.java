package com.karim.jwt.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * @author : sblim
 * @version : 1.0.0
 * @package : com.karim.jwt.dto
 * @name : spring-basic-server
 * @date : 2023. 04. 27. 027 오후 2:29
 * @modifyed :
 * @description :
 **/
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {

    @NotNull
    @Size(min = 3, max = 50)
    private String username;

    @NotNull
    @Size(min = 3, max = 100)
    private String password;
}