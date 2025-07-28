package com.demo.kafka_spring.producer;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * @author : sblim
 * @version : 1.0.0
 * @package : com.demo.kafka_spring.producer
 * @name : spring-basic-server
 * @date : 2025. 07. 25. 금 오후 5:49
 * @modifyed :
 * @description :
 **/

@Service
@RequiredArgsConstructor
public class KafkaProducerConfig {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String msg, String topic) {

        System.out.printf("Produce topic : %s, message : %s%n", topic, msg);
        kafkaTemplate.send(topic, msg);
    }
}