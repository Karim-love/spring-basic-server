package com.karim.springmetric.metric.basic;

import io.micrometer.core.instrument.Measurement;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.search.Search;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.MetricsEndpoint;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

/**
 * @author : sblim
 * @version : 1.0.0
 * @package : com.karim.springmetric.metric.basic
 * @name : spring-basic-server
 * @date : 2024. 01. 25. 025 오후 5:24
 * @modifyed :
 * @description :
 **/

@Service
@RequiredArgsConstructor
public class BasicMetricService {

    private final MeterRegistry meterRegistry;

    public Iterable<Measurement> getHttpRequestCount() {
        Iterable<Measurement> httpInfoResponse;
        httpInfoResponse = meterRegistry.get("http.server.requests").timer().measure();
        return httpInfoResponse;
    }
}
