package roomescape.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class MemberTest {

    @ParameterizedTest
    @CsvSource(value = {"1234, false", "12345, true"})
    @DisplayName("비밀번호가 올바른지 확인한다.")
    void isCorrectPassword(String password, boolean expected) {
        // given
        Member member = new Member("kargo", "kargo@google.com", "1234");

        // when
        boolean result = member.isNotCorrectPassword(password);

        // then
        assertThat(result).isEqualTo(expected);
    }
}
