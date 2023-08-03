package hello.servlet.web.frontcontroller.v2.controller;

import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;
import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v2.ControllerV2;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * @author : sblim
 * @version : 1.0.0
 * @package : hello.servlet.web.frontcontroller.v1.controller
 * @name : spring-basic-server
 * @date : 2023. 08. 02. 002 오후 1:35
 * @modifyed :
 * @description :
 **/
public class MemberSaveControllerV2 implements ControllerV2 {

    MemberRepository memberRepository = MemberRepository.getInstance();
    @Override
    public MyView process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String username = req.getParameter("username");
        int age = Integer.parseInt(req.getParameter("age"));

        Member member = new Member(username, age);

        memberRepository.save(member);

        // model에 데이터를 보관
        req.setAttribute("member", member);

        return new MyView("/WEB-INF/views/save-result.jsp");
    }
}
