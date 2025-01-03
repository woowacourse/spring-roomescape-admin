package roomescape.service.util;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
class TokenProviderTest {

    @Autowired
    private TokenProvider tokenProvider;

    @Test
    @DisplayName("생성한 토큰으로 userId를 가져올 수 있다.")
    void generateTokenAndParseUserId() {
        // given
        Long userId = 1L;
        String token = tokenProvider.generateToken(userId);

        // when
        Long result = tokenProvider.parseUserId(token);

        // then
        assertThat(result).isEqualTo(userId);
    }
}
