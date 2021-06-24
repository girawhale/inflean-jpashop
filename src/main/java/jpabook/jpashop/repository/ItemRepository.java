package jpabook.jpashop.repository;

import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long>
        , QuerydslPredicateExecutor<Item> { // QueryDsl을 사용할 수 있게 됨. 하지만 join과 fetch 등 사용 불가
}

/* Spring Data JPA 리팩토링 전 코드
@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    public void save(Item item) {
        if (item.getId() == null) { // id가 없다는 것은 새로 생성한 객체
            em.persist(item);
        } else { // 이미 DB에 등록된 객체
            em.merge(item);
        }
    }

    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }

    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }

}
*/
