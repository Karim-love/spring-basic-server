package hello.thymeleaf.basic;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author : sblim
 * @version : 1.0.0
 * @package : hello.thymeleaf.basic
 * @name : spring-basic-server
 * @date : 2024. 01. 08. 008 오전 10:49
 * @modifyed :
 * @description :
 **/

@Controller
@RequestMapping("/basic")
public class BasicController {

    @GetMapping("/text-basic")
    public String textBasic(Model model) {
        model.addAttribute("data", "Hello Spring");
        return "basic/text-basic";
    }

    /**
     * 이스케이프 미 사용법
     * @param model
     * @return
     */
    @GetMapping("/text-unescaped")
    public String textUnescaped(Model model) {
        model.addAttribute("data", "<b>Hello Spring</b>");
        return "basic/text-unescaped";
    }
}
