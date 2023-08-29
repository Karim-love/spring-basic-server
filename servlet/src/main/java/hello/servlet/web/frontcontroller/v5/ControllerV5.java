
package hello.servlet.web.frontcontroller.v5;

import java.util.Map;

/**
 * @author : sblim
 * @version : 1.0.0
 * @package : hello.servlet.web.frontcontroller.v4
 * @name : spring-basic-server
 * @date : 2023. 08. 24. 002 오후 1:29
 * @modifyed :
 * @description : 인터페이스에 ModelView가 없고, model 객체는 파라미터로 전달되기 때문에 그냥 사용하면 되고
 *                결과로 뷰의 이름만 반환해주면 된다.
 **/
public interface ControllerV5 {

    String process(Map<String, String> paramMap, Map<String, Object> model);
}
