package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Member> findByName(String name);
}


/*  Spring Data JPA 적용 전 코드

@Repository
@RequiredArgsConstructor
public class MemberRepository {
//    @PersistenceContext // 스프링이 자동으로 entityManager를 주입
//    @Autowired // spring data jpa는 요것도 가능. 때문에 생성자 주입이 가능!
    private final EntityManager em;

//    @PersistenceUnit // 요것도 가능!
//    private EntityManagerFactory entityManagerFactory;


    public void save(Member member) {
        em.persist(member);
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class) // entity 객체에 대한 쿼리로 조회
                .getResultList();
    }

    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }
}
 */

