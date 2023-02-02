package com.karim.spring.basic.server.order;

/**
 * @author : sblim
 * @version : 1.0.0
 * @package : com.karim.spring.basic.server.order
 * @name : spring-basic-server
 * @date : 2023. 02. 02. 002 오후 6:13
 * @modifyed :
 * @description :
 **/
public interface OrderService {

    /**
     * @param memberId
     * @param itemName
     * @param itemPrice
     * @return 주문 생성
     */
    Order createOrder(Long memberId, String itemName, int itemPrice);

}
