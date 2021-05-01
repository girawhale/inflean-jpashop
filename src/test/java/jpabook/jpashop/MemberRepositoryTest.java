package jpabook.jpashop;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberRepositoryTest {

//    @Autowired
//    MemberRepository memberRepository;
//
//    @Test
//    @Transactional // Test코드에 해당 애노테이션이 붙으면 test가 종료되면 자동으로 rollback
//    @Rollback(false)
//    public void testMember() throws Exception {
//        //given
//        Member member=new Member();
//        member.setUsername("memberA");
//
//        //when
//        Long saveId = memberRepository.save(member);
//        Member findMember = memberRepository.find(saveId);
//
//        //then
//        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
//        Assertions.assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
//
//        // 같은 트랜잭션 내에서는 같은 영속성 컨텍스트를 조회하기 때문에 같은 식별자를 가짐
//        // 1차 캐시내에서 같은 객체를 조회하기 때문에 true가 발생
//        // JPA 엔티티 동일성 보장
//        Assertions.assertThat(findMember).isEqualTo(member);
//    }

}