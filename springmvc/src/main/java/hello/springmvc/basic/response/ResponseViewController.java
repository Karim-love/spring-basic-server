package hello.springmvc.basic.response;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author : sblim
 * @version : 1.0.0
 * @package : hello.springmvc.basic.response
 * @name : spring-basic-server
 * @date : 2023. 12. 28. 028 오전 10:54
 * @modifyed :
 * @description :
 **/

@Controller
public class ResponseViewController {

    @RequestMapping("/response-view-v1")
    public ModelAndView responseViewV1() {
        ModelAndView modelAndView = new ModelAndView("response/hello")
                .addObject("data", "hello!");
        return modelAndView;
    }

    @RequestMapping("/response-view-v2")
    public String responseViewV2(Model model) {
        model.addAttribute("data", "hello!!");
        return "response/hello";
    }

    // rest의 경로와 템플릿의 경로가 같으면 리턴을 안해도 된다.
    // 불명확해서 사용을 권장하지 않음
    @RequestMapping("response/hello")
    public void responseViewV3(Model model) {
        model.addAttribute("data", "hello!!");
    }
}
