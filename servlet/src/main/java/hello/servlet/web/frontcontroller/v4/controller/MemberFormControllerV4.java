package hello.servlet.web.frontcontroller.v4.controller;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.v3.ControllerV3;
import hello.servlet.web.frontcontroller.v4.ControllerV4;

import java.util.Map;

/**
 * @author : sblim
 * @version : 1.0.0
 * @package : hello.servlet.web.frontcontroller.v1.controller
 * @name : spring-basic-server
 * @date : 2023. 08. 02. 002 오후 1:31
 * @modifyed :
 * @description :
 **/
public class MemberFormControllerV4 implements ControllerV4 {

    @Override
    public String process(Map<String, String> paramMap, Map<String, Object> model) {
        return "new-form";
    }
}
