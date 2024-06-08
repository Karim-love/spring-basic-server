package hello.itemservice.validation;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.validation.DefaultMessageCodesResolver;
import org.springframework.validation.MessageCodesResolver;

/**
 * @author : sblim
 * @version : 1.0.0
 * @package : hello.itemservice.validation
 * @name : spring-basic-server
 * @date : 2024. 06. 05. 수 오후 12:56
 * @modifyed :
 * @description :
 **/
public class MessageCodesResolverTest {

    MessageCodesResolver messageCodesResolver =  new DefaultMessageCodesResolver();

    @Test
    void messageCodesResolverObject(){
        String[] messageCodes = messageCodesResolver.resolveMessageCodes("required", "item");
        Assertions.assertThat(messageCodes).containsExactly("required.item", "required");
    }

    @Test
    void messageCodesResolverField(){
        String[] messageCodes = messageCodesResolver.resolveMessageCodes("required", "item", "itemName", String.class);
        Assertions.assertThat(messageCodes).containsExactly("required.item.itemName", "required.itemName",
                                                                     "required.java.lang.String", "required");
    }
}
