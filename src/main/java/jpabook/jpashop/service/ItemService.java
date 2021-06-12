package jpabook.jpashop.service;

import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    @Transactional // readOnly라면 저장 안댐
    public void saveItem(Item item){
        itemRepository.save(item);
    }

    @Transactional
    public void updateItem(Long itemId, String name, int price, int stockQuantity) {
        Item findItem = itemRepository.findOne(itemId);
        findItem.setName(name);
        findItem.setPrice(price);
        findItem.setStockQuantity(stockQuantity);

//        itemRepository.save(findItem); 호출할 필요가 없음. flush할 때, 변경을 감지하고 자동으로 update쿼리를 날림
    }

    public Item findOne(long itemId){
        return itemRepository.findOne(itemId);
    }

    public List<Item> findItems(){
        return itemRepository.findAll();
    }

}
