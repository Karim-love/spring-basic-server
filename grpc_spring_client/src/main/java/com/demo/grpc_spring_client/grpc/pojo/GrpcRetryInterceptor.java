package com.demo.grpc_spring_client.grpc.pojo;

import io.grpc.CallOptions;
import io.grpc.Channel;
import io.grpc.ClientCall;
import io.grpc.ClientInterceptor;
import io.grpc.ForwardingClientCall;
import io.grpc.ForwardingClientCallListener;
import io.grpc.Metadata;
import io.grpc.MethodDescriptor;
import io.grpc.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * @author : sblim
 * @version : 1.0.0
 * @package : com.demo.grpc_spring_client.grpc.pojo
 * @name : spring-basic-server
 * @date : 2025. 07. 31. 목 오후 2:43
 * @modifyed :
 * @description :
 **/

public class GrpcRetryInterceptor implements ClientInterceptor {

    private static final Logger log = LoggerFactory.getLogger(GrpcRetryInterceptor.class);

    private final int maxRetries;
    private final long retryDelayMillis; // 재시도 간격

    public GrpcRetryInterceptor(int maxRetries, long retryDelayMillis) {
        this.maxRetries = maxRetries;
        this.retryDelayMillis = retryDelayMillis;
    }

    @Override
    public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(
            MethodDescriptor<ReqT, RespT> method, CallOptions callOptions, Channel next) {

        return new ForwardingClientCall.SimpleForwardingClientCall<ReqT, RespT>(next.newCall(method, callOptions)) {
            private int attempt = 0;

            @Override
            public void start(Listener<RespT> responseListener, Metadata headers) {
                // 실제 호출을 시작하는 부분
                // 응답 리스너를 커스텀하여 에러 발생 시 재시도 로직을 처리합니다.
                super.start(new ForwardingClientCallListener.SimpleForwardingClientCallListener<RespT>(responseListener) {
                    @Override
                    public void onClose(Status status, Metadata trailers) {
                        if (status.isOk()) {
                            // 성공적으로 응답을 받았을 경우
                            super.onClose(status, trailers);
                            return;
                        }

                        // 에러가 발생했을 경우
                        if (canRetry(status) && attempt < maxRetries) {
                            attempt++;
                            log.warn("gRPC 호출 실패 ({}): {}, 재시도 ({}/{}) 중...",
                                    method.getFullMethodName(), status.getDescription(), attempt, maxRetries);

                            try {
                                // 지정된 지연 시간만큼 기다린 후 재시도
                                TimeUnit.MILLISECONDS.sleep(retryDelayMillis);
                                // 새로운 ClientCall을 생성하여 다시 시작
                                next.newCall(method, callOptions).start(this, headers);
                                return;
                            } catch (InterruptedException e) {
                                log.error("재시도 대기 중 인터럽트 발생: {}", e.getMessage());
                                Thread.currentThread().interrupt(); // 인터럽트 상태 복원
                            }
                        }

                        // 재시도 불가능하거나 최대 재시도 횟수를 초과했을 경우
                        log.error("gRPC 호출 최종 실패 ({}): {}, 재시도 횟수: {}",
                                method.getFullMethodName(), status.getDescription(), attempt);
                        super.onClose(status, trailers); // 원래의 리스너로 에러 전달
                    }
                }, headers);
            }

            // 재시도 가능한 gRPC 상태 코드를 정의합니다.
            // 서버가 죽었을 경우 보통 UNAVAILABLE, UNKNOWN, ABORTED 등이 발생할 수 있습니다.
            private boolean canRetry(Status status) {
                Status.Code code = status.getCode();
                return code == Status.Code.UNAVAILABLE || // 서버에 연결할 수 없을 때
                       code == Status.Code.UNKNOWN ||     // 알 수 없는 에러 (네트워크 문제 등)
                       code == Status.Code.ABORTED ||     // 요청이 중단되었을 때
                       code == Status.Code.DEADLINE_EXCEEDED; // 타임아웃
            }
        };
    }
}