package com.demo.grpc_spring_client.grpc.pojo;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : sblim
 * @version : 1.0.0
 * @package : com.demo.grpc_spring_client.grpc.pojo
 * @name : spring-basic-server
 * @date : 2025. 07. 31. 목 오후 1:58
 * @modifyed :
 * @description :
 **/

/**
 * gRPC 클라이언트의 ManagedChannel을 스프링 빈으로 설정하는 클래스입니다.
 * GrpcClientProperties에서 서버 호스트와 포트 정보를 가져와 ManagedChannel을 구축합니다.
 */
@Configuration // 스프링 설정 클래스로 지정
public class GrpcChannelConfig {

    /**
     * gRPC 서버 연결을 위한 ManagedChannel 빈을 생성합니다.
     * ManagedChannel은 gRPC 통신을 위한 로우 레벨 연결을 나타냅니다.
     *
     * @return 구성된 ManagedChannel 인스턴스
     */
    @Bean(destroyMethod = "shutdown") // 스프링 컨테이너 종료 시 채널을 안전하게 종료하도록 destroyMethod 지정
    public ManagedChannel grpcManagedChannel() {
        // ManagedChannelBuilder를 사용하여 서버 호스트와 포트로 채널을 구축합니다.
        return ManagedChannelBuilder.forAddress(
                        "127.0.0.1", // application.yml에서 가져온 호스트
                        9090)  // application.yml에서 가져온 포트
                .usePlaintext() // 개발 환경에서는 보안 없이 평문 통신을 사용합니다.
                                // 프로덕션 환경에서는 .useTransportSecurity()를 사용하여 TLS/SSL을 구성해야 합니다.
                .build();
    }
}
