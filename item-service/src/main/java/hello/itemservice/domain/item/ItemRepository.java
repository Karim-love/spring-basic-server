package hello.itemservice.domain.item;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author : sblim
 * @version : 1.0.0
 * @package : hello.itemservice.domain.item
 * @name : spring-basic-server
 * @date : 2024. 01. 04. 004 오전 10:05
 * @modifyed :
 * @description :
 **/

@Repository
public class ItemRepository {

    private static final Map<Long, Item> store = new ConcurrentHashMap<>();
    private static final AtomicLong sequence = new AtomicLong(0L);

    public Item save(Item item){
        item.setId(sequence.incrementAndGet());
        store.put(item.getId(), item);
        return item;
    }

    public Item findByItem(Long id){
        return store.get(id);
    }

    public List<Item> findAll(){
        return new ArrayList<>(store.values());
    }

    public void update(Long id, UpdateParamDto updateParamDto) {
        Item findItem = findByItem(id);
        findItem.setItemName(updateParamDto.getItemName());
        findItem.setPrice(updateParamDto.getPrice());
        findItem.setQuantity(updateParamDto.getQuantity());
    }

    public void clearStore(){
        store.clear();
    }
}
