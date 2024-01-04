package hello.itemservice.domain.item;

import lombok.Getter;
import lombok.Setter;

/**
 * @author : sblim
 * @version : 1.0.0
 * @package : hello.itemservice.domain.item
 * @name : spring-basic-server
 * @date : 2024. 01. 04. 004 오전 9:59
 * @modifyed :
 * @description :
 **/

@Getter
@Setter
public class UpdateParamDto {

    private String itemName;
    private Integer price;
    private Integer quantity;

    public UpdateParamDto() {
    }

    public UpdateParamDto(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
