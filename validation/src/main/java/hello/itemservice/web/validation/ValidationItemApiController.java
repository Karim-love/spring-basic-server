package hello.itemservice.web.validation;

import hello.itemservice.web.validation.form.ItemSaveForm;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/validation/api/items")
@Tag(name = "검증", description = "Item 등록 시 검증 로직")
public class ValidationItemApiController {

    @PostMapping("/add")
    public Object addItem(@RequestBody @Validated ItemSaveForm itemSaveForm, BindingResult bindingResult){

        log.info("API 호출");

        if (bindingResult.hasErrors()){
            log.error("검증 오류 발생 errors = {}", bindingResult);
            return bindingResult.getAllErrors();
        }

        log.info("성공 로직");
        return itemSaveForm;
    }
}
