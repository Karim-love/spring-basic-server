package com.karim.springmetric.metric.timer;

import com.karim.springmetric.metric.gauge.CustomMetricMemoryUseService;
import io.micrometer.core.instrument.FunctionTimer;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * @author : sblim
 * @version : 1.0.0
 * @package : com.karim.springmetric.metric.timer
 * @name : spring-basic-server
 * @date : 2024. 01. 25. 025 오후 4:42
 * @modifyed :
 * @description :
 **/

@Service
public class CustomMetricTimerService {

    private final FunctionTimer functionTimer;
    private int methodCount = 1;

    @Autowired
    public CustomMetricTimerService(MeterRegistry meterRegistry) {

        this.functionTimer = FunctionTimer.builder("custom.timer", this,
                        CustomMetricTimerService::getCount,
                        CustomMetricTimerService::getTotalTime,
                        TimeUnit.MILLISECONDS)
                .register(meterRegistry);
    }

    private long getCount() {

        return methodCount;
    }

    private long getTotalTime() {

        methodCount++;
        long startTime = System.currentTimeMillis();
        testMethod();
        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }

    private void testMethod() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
