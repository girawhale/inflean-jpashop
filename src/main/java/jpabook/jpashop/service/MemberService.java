package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.*;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) // jpa를 사용할 때, 데이터 변경이나 조회는 트랜잭션 안에서 일어나야 함. public 메소드에는 자동으로 붙여줌
@RequiredArgsConstructor // final이 붙은 필드를 대상으로 생성자를 만들어줌
public class MemberService {

    //    @Autowired// 필드기 때문에 변경이 불가능. 테스트 코드의 작성의 어려움
    private final MemberRepository memberRepository;

    @Transactional // 읽기전용 메소드가 많기때문에 전역적으로 readonly를 붙여주고 쓰기가 진행되는 메소드에만 별도로 붙임
    public Long join(Member member) {
        validateDuplicateMember(member);

        memberRepository.save(member);
        return member.getId();
    }

    // 동시에 조회를 하고 저장하는 경우 동시에 insert가 발생할 수 있다
    // 때문에 최후에 한 번 더 거르는 로직이 필요
    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원");
        }
    }

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }

    @Transactional
    public void update(Long id, String name) {
        Member member = memberRepository.findOne(id);
        member.setName(name);
    }
}
