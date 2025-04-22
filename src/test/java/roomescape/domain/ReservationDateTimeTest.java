package roomescape.domain;

import static org.assertj.core.api.Assertions.*;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;
import roomescape.common.Constant;

class ReservationDateTimeTest {

    private final LocalDateTime now = LocalDateTime.now(Constant.FIXED_CLOCK);
    private final Clock clock = Constant.FIXED_CLOCK;

    @Test
    void 예약_날짜와_시간을_올바르게_생성한다() {
        // given
        ReservationDate date = new ReservationDate(LocalDate.of(2025, 5, 1));
        ReservationTime time = new ReservationTime(1L, LocalTime.of(14, 30));

        // when
        ReservationDateTime reservationDateTime = new ReservationDateTime(date, time, clock);

        // then
        assertThat(reservationDateTime.reservationTime()).isEqualTo(time);
        assertThat(reservationDateTime.reservationDate()).isEqualTo(date);
    }

    @Test
    void 예약_날짜는_null일_수_없다() {
        // given
        ReservationTime time = new ReservationTime(1L, LocalTime.of(14, 30));

        // when & then
        assertThatThrownBy(() -> new ReservationDateTime(null, time, clock))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("예약 날짜는 null일 수 없습니다.");
    }

    @Test
    void 예약_시간은_null일_수_없다() {
        // given
        ReservationDate date = new ReservationDate(LocalDate.of(2025, 5, 1));

        // when & then
        assertThatThrownBy(() -> new ReservationDateTime(date, null, clock))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("예약 시간은 null일 수 없습니다.");
    }

    @Test
    void 미래_시간의_예약을_생성할_수_있다() {
        // given
        ReservationDate futureDate = new ReservationDate(now.toLocalDate().plusDays(1));
        ReservationTime time = new ReservationTime(1L, now.toLocalTime());

        // when & then
        assertThatCode(() -> new ReservationDateTime(futureDate, time, clock));
    }

    @Test
    void 과거_시간의_예약은_예외가_발생한다() {
        // given
        ReservationDate pastDate = new ReservationDate(now.toLocalDate().minusDays(1));
        ReservationTime time = new ReservationTime(1L, LocalTime.of(14, 30));

        // when & then
        assertThatThrownBy(() -> new ReservationDateTime(pastDate, time, clock))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("예약 일시는 현재 이후여야 합니다.");
    }

    @Test
    void 같은_날_미래_시간의_예약을_생성할_수_있다() {
        // given
        ReservationDate today = new ReservationDate(now.toLocalDate());
        ReservationTime futureTime = new ReservationTime(1L, now.toLocalTime().plusHours(1));

        // when & then
        assertThatCode(() -> new ReservationDateTime(today, futureTime, clock));    }

    @Test
    void 같은_날_과거_시간의_예약은_isAfter가_아니다() {
        // given
        ReservationDate today = new ReservationDate(now.toLocalDate());
        ReservationTime pastTime = new ReservationTime(1L, now.toLocalTime().minusHours(1));

        // when & then
        assertThatThrownBy(() -> new ReservationDateTime(today, pastTime, clock))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("예약 일시는 현재 이후여야 합니다.");
    }
}
