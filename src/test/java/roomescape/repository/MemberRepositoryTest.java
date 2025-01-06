package roomescape.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static roomescape.fixture.MemberFixture.MEMBER_1_ID;
import static roomescape.fixture.MemberFixture.NOT_EXIST_ID;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import roomescape.domain.Member;
import roomescape.fixture.MemberFixture;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Sql(scripts = "/truncate.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/test_data.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
class MemberRepositoryTest {

    @Autowired
    private MemberRepository repository;

    @Test
    @DisplayName("회원을 추가한다.")
    void save() {
        // given
        Member member = MemberFixture.newMemberWithoutId();

        // when
        Member savedMember = repository.save(member);

        // then
        Member found = repository.findById(savedMember.getId()).get();
        assertThat(savedMember).isEqualTo(found);
    }

    @Test
    @DisplayName("회원을 id로 가져온다.")
    void findById() {
        // when
        Member found = repository.findById(MEMBER_1_ID).get();

        // then
        assertThat(found.getName()).isEqualTo(MemberFixture.member1().getName());
    }

    @Test
    @DisplayName("존재하지 않는 id로 회원을 조회하면 비어있는 Optional 객체를 반환한다.")
    void findByNotExistsId() {
        // when
        Optional<Member> found = repository.findById(NOT_EXIST_ID);

        // then
        assertThat(found).isEmpty();
    }

    @Test
    @DisplayName("이메일로 회원을 조회한다.")
    void findByEmail() {
        // when
        Member found = repository.findByEmail(MemberFixture.member1().getEmail()).get();

        // then
        assertThat(found.getName()).isEqualTo(MemberFixture.member1().getName());
    }

    @Test
    @DisplayName("모든 회원을 조회한다.")
    void findAll() {
        // when
        List<Member> found = repository.findAll();

        // then
        assertThat(found.size()).isEqualTo(MemberFixture.INITIAL_MEMBER_SIZE);
    }
}
