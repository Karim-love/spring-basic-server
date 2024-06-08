package hello.itemservice.validation;

import hello.itemservice.domain.item.Item;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class BeanValidationTest {

    @Test
    void BeanValidation(){

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

        Validator validator = factory.getValidator();

        Item item = new Item();
        item.setItemName(" ");
        item.setPrice(0);
        item.setQuantity(10000);

        Set<ConstraintViolation<Item>> validate = validator.validate(item);

        for (ConstraintViolation<Item> itemConstraintViolation : validate) {
            System.out.println(itemConstraintViolation);
            System.out.println(itemConstraintViolation.getMessage());
        }
    }
}