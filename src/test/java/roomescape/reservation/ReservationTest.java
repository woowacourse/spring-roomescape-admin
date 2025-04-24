package roomescape.reservation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import roomescape.time.ReservationTime;

class ReservationTest {
    @DisplayName("id가_같은지_여부를_반환한다")
    @Test
    void isIdEquals() {
        // given
        Reservation reservation = new Reservation(1L, "레오", LocalDate.now(), new ReservationTime(1L, LocalTime.now()));

        // when
        boolean result = reservation.isIdEquals(1L);

        // then
        assertThat(result).isTrue();
    }

    @DisplayName("유효하지_않은_예약자_이름이면_예외를_발생한다")
    @NullAndEmptySource
    @ValueSource(strings = {" "})
    @ParameterizedTest
    void should_ThrowException_WhenCustomerIsInvalid(String customerName) {
        // given
        LocalDate reservationDate = LocalDate.of(2025, 04, 24);
        ReservationTime reservationTime = new ReservationTime(1L, LocalTime.of(15, 0));

        // when
        // then
        assertThatThrownBy(() -> new Reservation(1L, customerName, reservationDate, reservationTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("유효하지 않은 예약자 이름입니다.");
    }

    @DisplayName("예약_날짜가_null이면_예외를_발생한다")
    @Test
    void should_ThrowException_WhenReservationDateIsNull() {
        // given
        ReservationTime reservationTime = new ReservationTime(1L, LocalTime.of(15, 0));

        // when
        // then
        assertThatThrownBy(() -> new Reservation(1L, "레오", null, reservationTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("예약 날짜는 null일 수 없습니다.");
    }

    @DisplayName("예약_시간이_null이면_예외를_발생한다")
    @Test
    void should_ThrowException_WhenReservationTimeIsNull() {
        // given
        LocalDate reservationDate = LocalDate.of(2025, 04, 24);

        // when
        // then
        assertThatThrownBy(() -> new Reservation(1L, "레오", reservationDate, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("예약 시간은 null일 수 없습니다.");
    }
}
