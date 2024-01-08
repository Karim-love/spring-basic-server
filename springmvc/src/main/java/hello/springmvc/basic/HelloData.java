package hello.springmvc.basic;

import lombok.Data;

/**
 * @author : sblim
 * @version : 1.0.0
 * @package : hello.springmvc.basic
 * @name : spring-basic-server
 * @date : 2023. 09. 01. 001 오후 3:41
 * @modifyed :
 * @description :
 **/

@Data
// @Data = @Getter, @Setter, @ToString, @EqualsAndHashCode, @RequiredArgsConstructor
public class HelloData {
    private String username;
    private int age;
}
