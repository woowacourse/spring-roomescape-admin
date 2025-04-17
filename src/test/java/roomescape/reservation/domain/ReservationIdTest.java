package roomescape.reservation.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ReservationIdTest {

    @Test
    @DisplayName("값이 설정되지 않았다면, 예외가 발생한다")
    void whenValueIsNullThrowException() {
        // given
        ReservationId id = ReservationId.from(null);

        // when
        // then
        assertThatThrownBy(id::getValue)
                .isInstanceOf(IllegalStateException.class);


    }
}
