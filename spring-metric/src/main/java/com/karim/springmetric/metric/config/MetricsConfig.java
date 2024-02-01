package com.karim.springmetric.metric.config;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.springframework.boot.actuate.metrics.MetricsEndpoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : sblim
 * @version : 1.0.0
 * @package : com.karim.springmetric.metric.config
 * @name : spring-basic-server
 * @date : 2024. 01. 26. 026 오후 2:07
 * @modifyed :
 * @description :
 **/
@Configuration
public class MetricsConfig {

    @Bean
    public MeterRegistry meterRegistry() {
        // 내장형 레지스트리를 사용하거나, 필요에 따라 다른 레지스트리를 여기에 등록할 수 있습니다.
        return new SimpleMeterRegistry();
    }

    @Bean
    public MetricsEndpoint metricsEndpoint(MeterRegistry meterRegistry) {
        return new MetricsEndpoint(meterRegistry);
    }
}