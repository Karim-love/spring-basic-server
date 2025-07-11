package com.karim.springmetric.metric.gauge;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author : sblim
 * @version : 1.0.0
 * @package : com.karim.springmetric.metric.gauge
 * @name : spring-basic-server
 * @date : 2024. 01. 25. 025 오전 9:50
 * @modifyed :
 * @description :
 **/

@Service
public class CustomMetricMemoryUseService {

    private final Gauge customGauge;
    private double gaugeValue; // Gauge의 값

    /**
     * 동적 값 표현: 게이지는 어떤 값의 순간적인 측정치를 나타내는 메트릭
     * 양수와 음수 값을 가질 수 있습니다. 값은 언제든지 변할 수 있음
     * 예시: 현재 시스템 메모리 사용량, 현재 연결된 사용자 수, 작업 큐의 길이 등.
     * @param meterRegistry
     */
    @Autowired
    public CustomMetricMemoryUseService(MeterRegistry meterRegistry) {
        // 게이지 생성
        this.customGauge = Gauge.builder("custom.memory.use.gauge", this, CustomMetricMemoryUseService::getGaugeValue)
                .description("A custom gauge")
                .register(meterRegistry);
    }

    // 현재 메모리 사용량
    // bytes
    public void performBusinessLogic() {
        Runtime.getRuntime().gc();
        gaugeValue = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
    }

    // Gauge 값 반환 메서드
    private double getGaugeValue() {

        // 메모리 사용률
        performBusinessLogic();

        return gaugeValue;
    }
}