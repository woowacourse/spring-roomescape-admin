package roomescape.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import roomescape.exception.BadRequestException;
import roomescape.fixture.MemberFixture;

class MemberTest {

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("공백이나 null로만 이루어진 이름으로 예약할 수 없다.")
    void createMemberFailByName(String name) {
        assertThatThrownBy(() -> MemberFixture.newMember(name))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("예약자의 이름은");
    }

    @Test
    @DisplayName("예약자의 이름은 20자를 초과할 수 없다.")
    void createMemberFailByTooLongName() {
        // given
        String name1 = Strings.repeat("A", 20);
        String name2 = Strings.repeat("A", 21);

        // when & then
        assertThatCode(() -> MemberFixture.newMember(name1))
                .doesNotThrowAnyException();
        assertThatThrownBy(() -> MemberFixture.newMember(name2))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("예약자의 이름은");
    }

    @ParameterizedTest
    @CsvSource(value = {"1234, false", "12345, true"})
    @DisplayName("비밀번호가 올바른지 확인한다.")
    void isCorrectPassword(String password, boolean expected) {
        // given
        Member member = new Member("kargo", "kargo@google.com", "1234", Role.ADMIN);

        // when
        boolean result = member.isNotCorrectPassword(password);

        // then
        assertThat(result).isEqualTo(expected);
    }
}
