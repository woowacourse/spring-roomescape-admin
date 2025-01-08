package roomescape.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static roomescape.fixture.MemberFixture.MEMBER_1_EMAIL;
import static roomescape.fixture.MemberFixture.NOT_EXIST_EMAIL;
import static roomescape.fixture.MemberFixture.PASSWORD;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import roomescape.domain.Member;
import roomescape.exception.BadRequestException;
import roomescape.fixture.MemberFixture;
import roomescape.service.dto.LoginRequest;
import roomescape.service.dto.LoginResponse;
import roomescape.service.util.TokenProvider;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
@Sql(scripts = "/truncate.sql", executionPhase = ExecutionPhase.BEFORE_TEST_CLASS)
@Sql(scripts = "/test_data.sql", executionPhase = ExecutionPhase.BEFORE_TEST_CLASS)
class LoginServiceTest {

    private static final long EXPIRATION = 1000;

    @TestConfiguration
    static class TestConfig {
        @Bean
        public TokenProvider tokenProvider() {
            return new TokenProvider("testSecretKeyFAWtnPJ2AFlL8WXmohJMCvigQggaEypa5E=", EXPIRATION);
        }
    }

    @Autowired
    private LoginService loginService;

    @Test
    @DisplayName("로그인에 성공하면 토큰을 반환한다.")
    void login() {
        // when
        LoginResponse loginResponse = loginService.login(MemberFixture.loginRequest1());

        // then
        assertThat(loginResponse.token()).isNotNull();
    }

    @Test
    @DisplayName("존재하지 않는 이메일로 로그인 시도시 예외를 발생시킨다.")
    void loginFailByEmail() {
        // when & then
        assertThatThrownBy(() -> loginService.login(new LoginRequest(NOT_EXIST_EMAIL, PASSWORD)))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("이메일을 다시 확인해주세요.");
    }

    @Test
    @DisplayName("비밀번호가 틀리면 예외를 발생시킨다.")
    void loginFailByPassword() {
        // when & then
        assertThatThrownBy(() -> loginService.login(new LoginRequest(MEMBER_1_EMAIL, PASSWORD + "aaa")))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("비밀번호가 일치하지 않습니다.");
    }

    @Test
    @DisplayName("토큰으로 회원을 찾는다")
    void findMemberByToken() {
        // given
        String token = loginService.login(MemberFixture.loginRequest1()).token();

        // when
        Member found = loginService.findMemberByToken(token);

        // then
        assertThat(found).isEqualTo(MemberFixture.member1());
    }

    @Test
    @DisplayName("유효하지 않은 토큰으로 회원을 찾으려 시도하면 예외를 발생시킨다.")
    void findMemberByTokenFail() {
        // given
        LoginResponse loginResponse = loginService.login(MemberFixture.loginRequest1());
        String invalidToken = loginResponse.token() + "123";

        // then & then
        assertThatThrownBy(() -> loginService.findMemberByToken(invalidToken))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("올바르지 않은 토큰입니다.");
    }

    @Test
    @DisplayName("만료된 토큰으로 회원을 찾으려 시도하면 예외를 발생시킨다.")
    void findMemberByExpiredToken() throws InterruptedException {
        // given
        LoginResponse loginResponse = loginService.login(MemberFixture.loginRequest1());
        Thread.sleep(EXPIRATION);
        assertThatThrownBy(() -> loginService.findMemberByToken(loginResponse.token()))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("만료된 토큰입니다.");
    }

    @Test
    @DisplayName("토큰이 관리자의 토큰이면 true를 반환한다.")
    void isAdminToken() {
        // given
        String token = loginService.login(MemberFixture.loginRequest1()).token();

        // when
        boolean result = loginService.isAdminToken(token);

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("토큰이 관리자의 토큰이 아니면 false를 반환한다.")
    void isNotAdminToken() {
        // given
        String token = loginService.login(MemberFixture.loginRequest2()).token();

        // when
        boolean result = loginService.isAdminToken(token);

        // then
        assertThat(result).isFalse();
    }
}
