package hello.servlet.web.frontcontroller.v4;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v4.ControllerV4;
import hello.servlet.web.frontcontroller.v4.controller.MemberFormControllerV4;
import hello.servlet.web.frontcontroller.v4.controller.MemberListControllerV4;
import hello.servlet.web.frontcontroller.v4.controller.MemberSaveControllerV4;
import jakarta.servlet.ServletException;
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

@WebServlet(name = "frontControllerServletV4", urlPatterns = "/front-controller/v4/*")
public class FrontControllerServletV4 extends HttpServlet {

    private final Map<String, ControllerV4> contollerMap = new HashMap<>();

    public FrontControllerServletV4() {
        contollerMap.put("/front-controller/v4/members/new-form", new MemberFormControllerV4());
        contollerMap.put("/front-controller/v4/members/save", new MemberSaveControllerV4());
        contollerMap.put("/front-controller/v4/members", new MemberListControllerV4());
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
        ControllerV4 controllerV4 = contollerMap.get(requestURI);

        if ( controllerV4 == null ){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        //paramMap
        Map<String, String> paramMap = createParamMap(request);
        Map<String, Object> model = new HashMap<>(); // 추가
        String viewName = controllerV4.process(paramMap, model);

        MyView view = viewResolver(viewName);

        view.render(model, request, response);
    }

    private MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }

    private Map<String, String> createParamMap(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<>();
        request.getParameterNames().asIterator()
                .forEachRemaining(paramName -> paramMap.put(paramName, request.getParameter(paramName)));
        return paramMap;
    }
}
