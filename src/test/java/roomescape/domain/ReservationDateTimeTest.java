package roomescape.domain;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;
import roomescape.common.Constant;

class ReservationDateTimeTest {

    private final LocalDateTime now = LocalDateTime.now(Constant.FIXED_CLOCK);

    @Test
    void 예약_날짜와_시간을_올바르게_생성한다() {
        // given
        ReservationDate date = new ReservationDate(LocalDate.of(2025, 5, 1));
        ReservationTime time = new ReservationTime(1L, LocalTime.of(14, 30));

        // when
        ReservationDateTime reservationDateTime = new ReservationDateTime(date, time);

        // then
        assertThat(reservationDateTime.reservationTime()).isEqualTo(time);
        assertThat(reservationDateTime.reservationDate()).isEqualTo(date);
    }

    @Test
    void 예약_날짜는_null일_수_없다() {
        // given
        ReservationTime time = new ReservationTime(1L, LocalTime.of(14, 30));

        // when & then
        assertThatThrownBy(() -> new ReservationDateTime(null, time))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("예약 날짜는 null일 수 없습니다.");
    }

    @Test
    void 예약_시간은_null일_수_없다() {
        // given
        ReservationDate date = new ReservationDate(LocalDate.of(2025, 5, 1));

        // when & then
        assertThatThrownBy(() -> new ReservationDateTime(date, null))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("예약 시간은 null일 수 없습니다.");
    }

    @Test
    void 미래_시간의_예약은_isAfter이다() {
        // given
        ReservationDate futureDate = new ReservationDate(now.toLocalDate().plusDays(1));
        ReservationTime time = new ReservationTime(1L, now.toLocalTime());
        ReservationDateTime reservationDateTime = new ReservationDateTime(futureDate, time);

        // when & then
        assertThat(reservationDateTime.isAfter(now)).isTrue();
    }

    @Test
    void 과거_시간의_예약은_isAfter가_아니다() {
        // given
        ReservationDate pastDate = new ReservationDate(now.toLocalDate().minusDays(1));
        ReservationTime time = new ReservationTime(1L, LocalTime.of(14, 30));
        ReservationDateTime reservationDateTime = new ReservationDateTime(pastDate, time);

        // when & then
        assertThat(reservationDateTime.isAfter(now)).isFalse();
    }

    @Test
    void 같은_날_미래_시간의_예약은_isAfter이다() {
        // given
        ReservationDate today = new ReservationDate(now.toLocalDate());
        ReservationTime futureTime = new ReservationTime(1L, now.toLocalTime().plusHours(1));

        ReservationDateTime reservationDateTime = new ReservationDateTime(today, futureTime);

        // when & then
        assertThat(reservationDateTime.isAfter(now)).isTrue();
    }

    @Test
    void 같은_날_과거_시간의_예약은_isAfter가_아니다() {
        // given
        ReservationDate today = new ReservationDate(now.toLocalDate());
        ReservationTime pastTime = new ReservationTime(1L, now.toLocalTime().minusHours(1));
        ReservationDateTime reservationDateTime = new ReservationDateTime(today, pastTime);

        // when & then
        assertThat(reservationDateTime.isAfter(now)).isFalse();
    }
}
