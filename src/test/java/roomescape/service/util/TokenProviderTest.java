package roomescape.service.util;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import roomescape.domain.Role;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
class TokenProviderTest {

    private static final Long USER_ID = 1L;

    @Autowired
    private TokenProvider tokenProvider;

    private String token;

    @Test
    @DisplayName("생성한 토큰으로 userId를 가져올 수 있다.")
    void generateTokenAndParseUserId() {
        // given
        String token = tokenProvider.generateToken(USER_ID, Role.ADMIN.toString());

        // when
        Long result = tokenProvider.parseMemberId(token);

        // then
        assertThat(result).isEqualTo(USER_ID);
    }

    @Test
    @DisplayName("생성한 토큰으로 role을 가져올 수 있다.")
    void generateTokenAndParseRole() {
        // given
        String token = tokenProvider.generateToken(USER_ID, Role.ADMIN.toString());

        // when
        String result = tokenProvider.parseRole(token);

        // then
        assertThat(result).isEqualTo(Role.ADMIN.toString());
    }
}
