package roomescape.domain;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;

class ReservationDateTimeTest {

    @Test
    void 예약_날짜와_시간을_올바르게_생성한다() {
        // given
        LocalDate date = LocalDate.of(2025, 5, 1);
        LocalTime time = LocalTime.of(14, 30);

        // when
        ReservationDateTime reservationDateTime = new ReservationDateTime(date, time);

        // then
        assertThat(reservationDateTime.date()).isEqualTo(date);
        assertThat(reservationDateTime.time()).isEqualTo(time);
    }

    @Test
    void 예약_날짜는_null일_수_없다() {
        // given
        LocalTime time = LocalTime.of(14, 30);

        // when & then
        assertThatThrownBy(() -> new ReservationDateTime(null, time))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("예약 날짜는 null일 수 없습니다.");
    }

    @Test
    void 예약_시간은_null일_수_없다() {
        // given
        LocalDate date = LocalDate.of(2025, 5, 1);

        // when & then
        assertThatThrownBy(() -> new ReservationDateTime(date, null))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("예약 시간은 null일 수 없습니다.");
    }

    @Test
    void 미래_시간의_예약은_isAfter이다() {
        // given
        LocalDate futureDate = LocalDate.now().plusDays(1);
        LocalTime time = LocalTime.of(14, 30);
        ReservationDateTime reservationDateTime = new ReservationDateTime(futureDate, time);

        LocalDateTime now = LocalDateTime.now();

        // when & then
        assertThat(reservationDateTime.isAfter(now)).isTrue();
    }

    @Test
    void 과거_시간의_예약은_isAfter가_아니다() {
        // given
        LocalDate pastDate = LocalDate.now().minusDays(1);
        LocalTime time = LocalTime.of(14, 30);
        ReservationDateTime reservationDateTime = new ReservationDateTime(pastDate, time);

        LocalDateTime now = LocalDateTime.now();

        // when & then
        assertThat(reservationDateTime.isAfter(now)).isFalse();
    }

    @Test
    void 같은_날_미래_시간의_예약은_isAfter이다() {
        // given
        LocalDate today = LocalDate.now();
        LocalTime futureTime = LocalTime.now().plusHours(1);
        ReservationDateTime reservationDateTime = new ReservationDateTime(today, futureTime);

        LocalDateTime now = LocalDateTime.now();

        // when & then
        assertThat(reservationDateTime.isAfter(now)).isTrue();
    }

    @Test
    void 같은_날_과거_시간의_예약은_isAfter가_아니다() {
        // given
        LocalDate today = LocalDate.now();
        LocalTime pastTime = LocalTime.now().minusHours(1);
        ReservationDateTime reservationDateTime = new ReservationDateTime(today, pastTime);

        LocalDateTime now = LocalDateTime.now();

        // when & then
        assertThat(reservationDateTime.isAfter(now)).isFalse();
    }
}
