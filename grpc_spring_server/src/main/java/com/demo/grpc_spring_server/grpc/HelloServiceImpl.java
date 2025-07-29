package com.demo.grpc_spring_server.grpc;

import com.example.grpc.service.HelloRequest;
import com.example.grpc.service.HelloResponse;
import com.example.grpc.service.HelloServiceGrpc;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;

/**
 * @author : sblim
 * @version : 1.0.0
 * @package : com.demo.grpc_spring_server.grpc
 * @name : spring-basic-server
 * @date : 2025. 07. 28. 월 오후 3:34
 * @modifyed :
 * @description :
 **/

@Slf4j
@GrpcService
public class HelloServiceImpl extends HelloServiceGrpc.HelloServiceImplBase {

    @Override
    public void requestMessage(HelloRequest request, StreamObserver<HelloResponse> responseObserver) {

        String requestMessage = request.getJsonString();

        log.info("클라이언트로부터 요청 수신: {}", requestMessage);

        // 응답 메시지 생성
        HelloResponse response = HelloResponse.newBuilder()
                .setJsonString("요청 받은 메세지 : " + requestMessage )
                .build();

        // 응답 전송
        // 생성된 응답 메시지를 클라이언트에게 전송합니다.
        // 'onNext()' 메서드는 응답 스트림에 하나의 메시지를 발행합니다.
        responseObserver.onNext(response);
        // 클라이언트에게 모든 응답 메시지 전송이 완료되었음을 알립니다.
        // 이 메서드를 호출하면 RPC 호출이 종료됩니다.
        responseObserver.onCompleted();
    }
}