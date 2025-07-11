package com.karim.springmetric.metric;

import com.karim.springmetric.metric.basic.BasicMetricService;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Measurement;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.MetricsEndpoint;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : sblim
 * @version : 1.0.0
 * @package : com.karim.springmetric.metric
 * @name : spring-basic-server
 * @date : 2024. 01. 25. 025 오전 9:35
 * @modifyed :
 * @description :
 **/

@RestController
public class CustomMetricController {

    private final Counter customCounter;
    private final BasicMetricService basicMetricService;

    @Autowired
    public CustomMetricController(MeterRegistry registry, BasicMetricService basicMetricService) {

        // 사용자 정의 메트릭 생성
        this.customCounter = Counter.builder("custom.counter")
                .register(registry);
        this.basicMetricService = basicMetricService;
    }

    /**
     * 증가 및 감소 가능: 카운터는 값이 오직 증가할 수 있는 메트릭
     * 주로 이벤트의 횟수를 계산할 때 사용
     * 즉, 항상 양수 값을 가지며, 값이 증가하면서 누적
     * 예시: API 호출 수, 오류 발생 수, 이벤트 발생 횟수 등.
     *
     * @return
     */
    @GetMapping("/increment")
    public String incrementCounter() {
        // 메트릭 증가
        customCounter.increment();
        return "Counter incremented!";
    }

    @Timed(value = "custom.controller.timer", description = "Custom Timer Description")
    @GetMapping("/method")
    public String getMethodTimer() throws InterruptedException {
        Thread.sleep(1000);
        return "execute method";
    }

    @GetMapping("/getTotalHttpRequests")
    public Iterable<Measurement> getTotalHttpRequests() {
        return basicMetricService.getHttpRequestCount();
    }
}
