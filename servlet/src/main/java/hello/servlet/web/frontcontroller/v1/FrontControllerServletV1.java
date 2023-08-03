package hello.servlet.web.frontcontroller.v1;

import hello.servlet.web.frontcontroller.v1.controller.MemberFormControllerV1;
import hello.servlet.web.frontcontroller.v1.controller.MemberListControllerV1;
import hello.servlet.web.frontcontroller.v1.controller.MemberSaveControllerV1;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : sblim
 * @version : 1.0.0
 * @package : hello.servlet.web.frontcontroller.v1
 * @name : spring-basic-server
 * @date : 2023. 08. 02. 002 오후 1:37
 * @modifyed :
 * @description :
 **/

@WebServlet(name = "frontControllerServletV1", urlPatterns = "/front-controller/v1/*")
public class FrontControllerServletV1 extends HttpServlet {

    private final Map<String, ControllerV1> contollerMap = new HashMap<>();

    public FrontControllerServletV1() {
        contollerMap.put("/front-controller/v1/members/new-form", new MemberFormControllerV1());
        contollerMap.put("/front-controller/v1/members/save", new MemberSaveControllerV1());
        contollerMap.put("/front-controller/v1/members", new MemberListControllerV1());
    }

    /**
     *
     * @param request the {@link HttpServletRequest} object that contains the request the client made of the servlet
     *
     * @param response the {@link HttpServletResponse} object that contains the response the servlet returns to the client
     *
     * @throws ServletException
     * @throws IOException
     * @Description requestURI를 조회해서 실제 호출할 컨트롤러를 controllerMap에서 찾는다.
     */
    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("FrontControllerServletV1.service");

        String requestURI = request.getRequestURI();
        ControllerV1 controllerV1 = contollerMap.get(requestURI);

        if ( controllerV1 == null ){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        controllerV1.process(request, response);
    }
}
