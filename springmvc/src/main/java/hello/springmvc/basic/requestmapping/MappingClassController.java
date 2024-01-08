package hello.springmvc.basic.requestmapping;

import org.springframework.web.bind.annotation.*;

/**
 * @author : sblim
 * @version : 1.0.0
 * @package : hello.springmvc.basic.requestmapping
 * @name : spring-basic-server
 * @date : 2023. 08. 31. 031 오전 11:55
 * @modifyed :
 * @description :
 * 회원 목록 조회 : GET   `/users`
 * 회원 등록     : POST   `/users`
 * 회원 조회     : GET    `/users/{userId}`
 * 회원 수정     : PATCH  `/users/{userId}`
 * 회원 삭제     : DELETE `/users/{userId}`
 **/

@RestController
@RequestMapping("/mapping/users")
public class MappingClassController {

    @GetMapping
    public String users() {
        return "get users";
    }

    @PostMapping
    public String addUser() {
        return "post user";
    }

    @GetMapping(value = "/{userId}")
    public String findUser(@PathVariable String userId) {
        return "find user = " + userId;
    }

    @PatchMapping(value = "/{userId}")
    public String updateUser(@PathVariable String userId) {
        return "update user = " + userId;
    }

    @DeleteMapping(value = "/{userId}")
    public String deleteUser(@PathVariable String userId) {
        return "delete user = " + userId;
    }
}
