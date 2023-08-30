package hello.servlet.web.springmvc.v1;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author : sblim
 * @version : 1.0.0
 * @package : hello.servlet.web.springmvc.v1
 * @name : spring-basic-server
 * @date : 2023. 08. 30. 030 오전 11:42
 * @modifyed :
 * @description :
 **/

@Controller // = @Component + @RequestMapping
// 스프링이 자동으로 스프링 빈을 등록한다. ( @Component )
// 스프링 MVC에서 어노테이션 기반 컨트롤러로 인식한다.
public class SpringMemberFormControllerV1 {

    // 요청 정보를 매핑한다.
    // 해단 URL이 호출되면 이 메소드가 호출된다.
    // 어노테이션을 기반으로 동작하기 때문에, 메소드의 이름은 임의로 지으면 된다.
    @RequestMapping("/springmvc/v1/members/new-form")
    public ModelAndView process(){
        // 모델과 뷰 정보를 담아서 반환하면 된다.
        return new ModelAndView("new-form");
    }
}
