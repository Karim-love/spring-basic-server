package hello.itemservice.domain.item;

/**
 * @author : sblim
 * @version : 1.0.0
 * @package : hello.itemservice.domain.item
 * @name : spring-basic-server
 * @date : 2024. 01. 11. 011 오전 11:48
 * @modifyed :
 * @description :
 **/

public enum ItemType {

    BOOK("도서"), FOOD("식품"), ETC("기타");

    private final String description;

    ItemType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
