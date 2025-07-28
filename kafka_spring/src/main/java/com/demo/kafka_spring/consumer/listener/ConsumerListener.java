package com.demo.kafka_spring.consumer.listener;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @author : sblim
 * @version : 1.0.0
 * @package : com.demo.kafka_spring.consumer.listener
 * @name : spring-basic-server
 * @date : 2025. 07. 28. 월 오전 9:21
 * @modifyed :
 * @description :
 **/
@Component
public class ConsumerListener {

    @KafkaListener(topics = "${topic.consumer.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(String message) {
        System.out.println("Received message: " + message);
    }
}