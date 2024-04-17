package roomescape.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ReservationTest {

    @Test
    @DisplayName("이미 초기화된 예약 ID를 초기화(수정)할 경우 예외가 발생한다.")
    void initializeId() {
        // given
        Reservation reservation = new Reservation("미아", LocalDate.now(), LocalTime.now());
        reservation.initializeId(1L);

        // when & then
        assertThatThrownBy(() -> reservation.initializeId(2L))
                .isInstanceOf(IllegalStateException.class);
    }

}
