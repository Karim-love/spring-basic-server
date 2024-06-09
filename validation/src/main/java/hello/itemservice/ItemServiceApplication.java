package hello.itemservice;

import hello.itemservice.web.validation.ItemValidation;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.validation.Validator;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class ItemServiceApplication{

	public static void main(String[] args) {
		SpringApplication.run(ItemServiceApplication.class, args);
	}

	// implements WebMvcConfigurer 후
	// 해당 메소드 오버라이드 하고 리턴을 커스텀한 검증기로 하면
	// 모든 controller 호출 시 해당 검증기 사용 가능
/*	@Override
	public Validator getValidator() {
		return new ItemValidation();
	}*/
}
