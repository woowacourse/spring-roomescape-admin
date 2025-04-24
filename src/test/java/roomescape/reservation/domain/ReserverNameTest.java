package roomescape.reservation.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class ReserverNameTest {

    @Test
    @DisplayName("예약자 이름은 비어있을 수 없다")
    void cannotEmptyReserverName() {
        // given
        // when
        // then
        assertAll(() -> {
            assertThatThrownBy(() -> ReserverName.from(String.valueOf(null)));
            assertThatThrownBy(() -> ReserverName.from(""));
        });
    }
}
