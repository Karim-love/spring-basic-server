package hello.servlet.web.springmvc.v3;

import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author : sblim
 * @version : 1.0.0
 * @package : hello.servlet.web.springmvc.v3
 * @name : spring-basic-server
 * @date : 2023. 08. 30. 030 오후 1:50
 * @modifyed :
 * @description : Model 도입, ViewName 직접 반환, @RequestParam 사용, @RequestMapping -> @GetMapping, @PostMapping
 **/

@Controller
@RequestMapping("/springmvc/v3/members")
public class SpringMemberControllerV3 {

    MemberRepository memberRepository = MemberRepository.getInstance();

//    @RequestMapping(value = "/new-form", method = RequestMethod.GET)
    @GetMapping("/new-form")
    public String newForm() {
        // 모델과 뷰 정보를 담아서 반환하면 된다.
        return "new-form";
    }

//    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @PostMapping("/save")
    public String save(@RequestParam("username") String username,
                        @RequestParam("age") int age,
                        Model model) {

        Member member = new Member(username, age);
        memberRepository.save(member);

        model.addAttribute("member", member);
        return "save-result";
    }

//    @RequestMapping(method = RequestMethod.GET)
    @GetMapping
    public String members(Model model) {

        List<Member> members = memberRepository.findAll();
        model.addAttribute("members", members);
        return "members";
    }
}
