package com.karim.springmetric.metric.gauge;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author : sblim
 * @version : 1.0.0
 * @package : com.karim.springmetric.metric.gauge
 * @name : spring-basic-server
 * @date : 2024. 01. 25. 025 오전 9:51
 * @modifyed :
 * @description :
 **/

@SpringBootTest
public class CustomMetricServiceTest {

    @Autowired
    private CustomMetricMemoryUseService customMetricMemoryUseService;

    @Test
    public void testCustomMetric() {

        // given
        MeterRegistry meterRegistry = new SimpleMeterRegistry();

        // when
        customMetricMemoryUseService.performBusinessLogic();

        // then
        assertThat(0.0).isLessThanOrEqualTo(meterRegistry.get("custom.gauge").gauge().value());
    }
}