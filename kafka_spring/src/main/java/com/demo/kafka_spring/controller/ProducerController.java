package com.demo.kafka_spring.controller;

import com.demo.kafka_spring.model.ProducerMessage;
import com.demo.kafka_spring.producer.KafkaProducerConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : sblim
 * @version : 1.0.0
 * @package : com.demo.kafka_spring.controller
 * @name : spring-basic-server
 * @date : 2025. 07. 28. 월 오전 9:27
 * @modifyed :
 * @description :
 **/

@RestController
@RequiredArgsConstructor
public class ProducerController {

    private final KafkaProducerConfig kafkaProducerConfig;

    @PostMapping("/kafka")
    // {"topic":"karim-send", "message":"test"}
    public String sendMsg(@RequestBody ProducerMessage producerMessage) {
        kafkaProducerConfig.sendMessage(producerMessage.getMessage(), producerMessage.getTopic());
        return "OK";
    }
}
