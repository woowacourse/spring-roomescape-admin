package roomescape.reservation.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class ReservationDateTimeTest {

    @Test
    @DisplayName("예약 가능한 시간이 아닐 때 예약하면 예외가 발생한다.")
    void whenInvalidTimeReservationThrowException() {
        //given
        //when
        //then
        assertThatThrownBy(() -> ReservationDateTime.from(LocalDateTime.of(
                LocalDate.now(),
                LocalTime.of(7, 0))))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("예약할 수 없는 시간입니다.");
    }
}
