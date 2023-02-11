package com.karim.spring.basic.server;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HelloLombok {

    private String name;
    private int age;

    public static void main(String[] args) {
        HelloLombok helloLombok = new HelloLombok();
        helloLombok.setAge(1);
        helloLombok.setName("a");

        System.out.println("helloLombok = " + helloLombok.toString());
    }
}
