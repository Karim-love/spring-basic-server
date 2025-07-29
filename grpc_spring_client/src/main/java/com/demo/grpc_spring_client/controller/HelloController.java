package com.demo.grpc_spring_client.controller;

import com.demo.grpc_spring_client.grpc.GrpcClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : sblim
 * @version : 1.0.0
 * @package : com.demo.grpc_spring_client.grpc.controller
 * @name : spring-basic-server
 * @date : 2025. 07. 28. 월 오후 4:54
 * @modifyed :
 * @description :
 **/
@RestController
@RequiredArgsConstructor
public class HelloController {

    private final GrpcClientService grpcClientService;

    @GetMapping("/hello")
    public String requestMessage(@RequestParam(name = "message" ) String message) {
        return grpcClientService.sendMessage(message);
    }
}