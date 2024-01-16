package hello.itemservice.domain.item;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author : sblim
 * @version : 1.0.0
 * @package : hello.itemservice.domain.item
 * @name : spring-basic-server
 * @date : 2024. 01. 11. 011 오전 11:51
 * @modifyed :
 * @description :
 **/

/**
 * FAST : 빠른 배송
 * NORMAL : 일반 배송
 * SLOW : 느린 배송
 */

@Data
@AllArgsConstructor
public class DeliveryCode {
    
    private String code;
    private String displayName;
}
