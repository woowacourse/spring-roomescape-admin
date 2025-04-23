package roomescape.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.domain.exception.EmptyReservationTimeException;

class ReservationTimeTest {

    @DisplayName("시간으로 객체를 생성할 수 있다.")
    @Test
    void createReservationTime() {
        // given

        // when & then
        assertThatCode(() -> new ReservationTime(LocalTime.of(9, 0)))
                .doesNotThrowAnyException();
    }

    @DisplayName("시간이 null이면 예외가 발생한다.")
    @Test
    void createNullTime() {
        // given

        // when & then
        assertThatThrownBy(() -> new ReservationTime(null))
                .isInstanceOf(EmptyReservationTimeException.class);
    }

}
