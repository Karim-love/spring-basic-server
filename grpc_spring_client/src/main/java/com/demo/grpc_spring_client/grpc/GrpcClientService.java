package com.demo.grpc_spring_client.grpc;

import com.example.grpc.service.HelloRequest;
import com.example.grpc.service.HelloResponse;
import com.example.grpc.service.HelloServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;

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

    @GrpcClient("local-grpc-server")
    private HelloServiceGrpc.HelloServiceBlockingStub helloServiceStub;
    @GrpcClient("local-grpc-server")
    private HelloServiceGrpc.HelloServiceStub helloServiceAsyncStub;

    public String sendMessage(String requestMessage) {

        // 동기 호출 시작 로그: 이 스레드가 블로킹될 것임을 알립니다.
        log.info("[동기 호출] 서버로 'requestMessage' 요청을 보냅니다. (현재 스레드: {})", Thread.currentThread().getName());

        HelloRequest request = HelloRequest.newBuilder()
                .setJsonString(requestMessage)
                .build();

        // 이 라인에서 응답이 올 때까지 현재 스레드가 블로킹됩니다.
        HelloResponse response = helloServiceStub.requestMessage(request);

        // 동기 호출 응답 수신 로그: 블로킹이 해제되고 응답을 받았을 때 찍힙니다.
        log.info("[동기 호출] 서버로부터 응답 수신: {} (현재 스레드: {})", response.getJsonString(), Thread.currentThread().getName());

        return response.getJsonString();
    }


    /**
     * gRPC 서버에 비동기 요청을 보내고, 응답을 CompletableFuture<String>으로 반환합니다.
     *
     * @param requestMessage 클라이언트가 서버로 보낼 메시지
     * @return gRPC 응답 문자열을 포함할 CompletableFuture
     */
    public CompletableFuture<String> sendMessageAsync(String requestMessage) {

        // 비동기 호출 시작 로그: 요청을 보내는 스레드 정보를 기록합니다.
        log.info("[비동기 호출] 서버로 'requestMessage' 비동기 요청을 보냅니다. (요청 스레드: {})", Thread.currentThread().getName());

        CompletableFuture<String> future = new CompletableFuture<>();

        HelloRequest request = HelloRequest.newBuilder()
                .setJsonString(requestMessage)
                .build();

        // 비동기 호출을 시작합니다. 이 메서드는 즉시 반환됩니다.
        helloServiceAsyncStub.requestMessage(request, new StreamObserver<HelloResponse>() {

            @Override
            public void onNext(HelloResponse response) {
                // 비동기 응답 수신 로그: 응답이 도착했을 때, gRPC 내부의 다른 스레드에서 이 콜백이 실행됩니다.
                log.info("[비동기 콜백] 서버로부터 비동기 응답 수신: {} (콜백 스레드: {})", response.getJsonString(), Thread.currentThread().getName());
                future.complete(response.getJsonString());
            }

            @Override
            public void onError(Throwable t) {
                // 비동기 오류 발생 로그: 오류가 발생했을 때, gRPC 내부의 다른 스레드에서 이 콜백이 실행됩니다.
                log.error("[비동기 콜백] 비동기 호출 중 오류 발생: {} (콜백 스레드: {})", t.getMessage(), Thread.currentThread().getName(), t);
                future.completeExceptionally(t);
            }

            @Override
            public void onCompleted() {
                // 비동기 호출 완료 로그: 콜백이 완료되었을 때 찍힙니다.
                log.info("[비동기 콜백] 비동기 호출 완료. (콜백 스레드: {})", Thread.currentThread().getName());
            }
        });

        // 비동기 요청을 보낸 직후, 요청을 보낸 스레드는 이 로그를 즉시 실행합니다.
        // 이는 해당 스레드가 블로킹되지 않고 다음 작업을 계속 진행했음을 보여줍니다.
        log.info("[비동기 호출] sendMessageAsync 메서드 종료. CompletableFuture 반환. (요청 스레드: {})", Thread.currentThread().getName());

        return future;
    }
}
