package hello.servlet.web.servletmvc;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "mvcMemberFormServlet", urlPatterns = "/servlet-mvc/members/new-form")
public class MvcMemberFormServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // /WEB-INF 이 경로안에 jsp가 있으면 외부에서 직접 호출할 수 없다.
        // 항상 컨트롤러를 통해서 호₩
        String viewPath = "/WEB-INF/views/new-form.jsp";
        RequestDispatcher dispatcher = req.getRequestDispatcher(viewPath);
        // 다른 서블릿이나 jsp로 이동
        // 서버 내부에서 다시 호출이 발생한다.
        dispatcher.forward(req, resp);
    }
}
