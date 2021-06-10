package jpabook.jpashop.Service;

import jpabook.jpashop.Repository.ItemRepository;
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

    public Item findOne(long itemId){
        return itemRepository.findOne(itemId);
    }

    public List<Item> findItems(){
        return itemRepository.findAll();
    }
}
