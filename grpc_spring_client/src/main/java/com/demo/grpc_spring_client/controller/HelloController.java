package com.demo.grpc_spring_client.controller;

import com.demo.grpc_spring_client.grpc.GrpcClientService;
import com.demo.grpc_spring_client.grpc.pojo.GrpcPojoClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

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
    private final GrpcPojoClientService grpcPojoClientService;

    @GetMapping("/hello")
    public String requestMessage(@RequestParam(name = "message" ) String message) {
        return grpcClientService.sendMessage(message);
    }

    @GetMapping("/helloAsync")
    public CompletableFuture<String> requestMessageAsync(@RequestParam(name = "message" ) String message) {

        return grpcClientService.sendMessageAsync(message);
    }

    @GetMapping("/pojo/hello")
    public String requestPojoMessage(@RequestParam(name = "message" ) String message) {
        return grpcPojoClientService.sendMessage(message);
    }

    @GetMapping("/pojo/helloAsync")
    public CompletableFuture<String> requestPojoMessageAsync(@RequestParam(name = "message" ) String message) {

        return grpcPojoClientService.sendMessageAsync(message);
    }
}