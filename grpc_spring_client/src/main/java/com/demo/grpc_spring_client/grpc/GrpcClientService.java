package com.demo.grpc_spring_client.grpc;


import com.example.grpc.service.HelloRequest;
import com.example.grpc.service.HelloResponse;
import com.example.grpc.service.HelloServiceGrpc;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

/**
 * @author : sblim
 * @version : 1.0.0
 * @package : com.demo.grpc_spring_client.grpc
 * @name : spring-basic-server
 * @date : 2025. 07. 28. 월 오후 4:53
 * @modifyed :
 * @description :
 **/
@Slf4j
@Service
public class GrpcClientService {

    /**
     * 스텁은 클라이언트 측에서 원격 서버의 서비스를 로컬 객체처럼 호출할 수 있게 해주는 프록시(Proxy) 객체
     * 클라이언트 코드와 실제 서버 간의 중간 다리 역할
     */
    // gRPC 서버의 Stub을 주입. blockingStub은 동기 호출을 지원합니다.
    @GrpcClient("local-grpc-server")
    private HelloServiceGrpc.HelloServiceBlockingStub helloServiceStub;

    public String sendMessage(String requestMessage) {
        log.info("서버로 'requestMessage' 요청을 보냅니다.");

        HelloRequest request = HelloRequest.newBuilder()
                .setJsonString(requestMessage)
                .build();

        HelloResponse response = helloServiceStub.requestMessage(request);
        log.info("서버로부터 응답 수신: {}", response.getJsonString());

        return response.getJsonString();
    }
}