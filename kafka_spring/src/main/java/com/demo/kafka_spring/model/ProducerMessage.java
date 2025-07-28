package com.demo.kafka_spring.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author : sblim
 * @version : 1.0.0
 * @package : com.demo.kafka_spring.model
 * @name : spring-basic-server
 * @date : 2025. 07. 28. 월 오전 9:31
 * @modifyed :
 * @description :
 **/
@Getter
@Setter
public class ProducerMessage {

    String topic;
    String message;
}
