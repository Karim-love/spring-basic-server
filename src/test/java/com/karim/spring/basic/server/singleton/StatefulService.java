package com.karim.spring.basic.server.singleton;

/**
 * @author : sblim
 * @version : 1.0.0
 * @package : com.karim.spring.basic.server.singleton
 * @name : spring-basic-server
 * @date : 2023. 02. 07. 007 오후 5:05
 * @modifyed :
 * @description :
 **/
public class StatefulService {

    private int price;

    public void order(String name, int price){
        System.out.println("name = " + name + " price = " + price);
        this.price = price;
    }

    public int getPrice() {
        return price;
    }
}
