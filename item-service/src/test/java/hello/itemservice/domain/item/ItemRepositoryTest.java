package hello.itemservice.domain.item;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

/**
 * @author : sblim
 * @version : 1.0.0
 * @package : hello.itemservice.domain.item
 * @name : spring-basic-server
 * @date : 2024. 01. 04. 004 오전 10:18
 * @modifyed :
 * @description :
 **/
public class ItemRepositoryTest {

    ItemRepository itemRepository = new ItemRepository();

    @AfterEach
    void afterEach(){
        itemRepository.clearStore();
    }

    @Test
    void save() {
        //given
        Item item = new Item("itemA", 10000, 10);

        //when
        Item saveItem = itemRepository.save(item);

        //then
        Item findItem = itemRepository.findByItem(item.getId());
        assertThat(findItem).isEqualTo(saveItem);
    }

    @Test
    void findAll() {
        //given
        Item itemA = new Item("item1", 10000, 10);
        Item itemB = new Item("item2", 20000, 20);
        itemRepository.save(itemA);
        itemRepository.save(itemB);

        //when
        List<Item> itemList = itemRepository.findAll();

        //then
        assertThat(itemList.size()).isEqualTo(2);
        assertThat(itemList).contains(itemA, itemB);
    }

    @Test
    void updateItem() {
        //given
        Item itemA = new Item("item1", 10000, 10);

        Item savedItem = itemRepository.save(itemA);
        Long itemId = savedItem.getId();

        //when
        UpdateParamDto updateParamDto = new UpdateParamDto("item2", 20000, 30);
        itemRepository.update(itemId, updateParamDto);

        //then
        Item findItem = itemRepository.findByItem(itemId);

        assertThat(findItem.getItemName()).isEqualTo(updateParamDto.getItemName());
        assertThat(findItem.getPrice()).isEqualTo(updateParamDto.getPrice());
        assertThat(findItem.getQuantity()).isEqualTo(updateParamDto.getQuantity());
    }
}
