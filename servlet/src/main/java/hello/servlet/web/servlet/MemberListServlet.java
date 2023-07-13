package hello.servlet.web.servlet;

import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * @author : sblim
 * @version : 1.0.0
 * @package : hello.servlet.web.servlet
 * @name : spring-basic-server
 * @date : 2023. 07. 13. 013 오전 9:43
 * @modifyed :
 * @description :
 **/

@WebServlet(name = "memberListServlet", urlPatterns = "/servlet/members")
public class MemberListServlet extends HttpServlet {
    MemberRepository memberRepository = MemberRepository.getInstance();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<Member> memberRepositoryAll = memberRepository.findAll();

        resp.setContentType("text/html");
        resp.setCharacterEncoding("utf-8");

        PrintWriter w = resp.getWriter();

        w.write("<html>\n");
        w.write("<head>\n");
        w.write("   <meta charset=\"UTF-8\">\n");
        w.write("</head>\n");
        w.write("<body>\n");
        for ( Member member : memberRepositoryAll ){
            w.write(member.getId()+"\n");
            w.write(member.getUsername()+"\n");
            w.write(member.getAge()+"\n");
        }
        w.write("<a href = \"/index.html\"> Main Page </a> \n");
        w.write("</body>\n");
        w.write("</html>\n");

    }
}
