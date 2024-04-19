package roomescape.domain.reservation;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import org.junit.jupiter.api.Test;

class ReservationDateTimeTest {
    private final Clock clock = Clock.fixed(Instant.parse("2024-04-18T00:00:00Z"), ZoneId.of("UTC"));

    @Test
    void 예약을_생성할_때_예약_날짜가_null이면_예외가_발생한다() {
        LocalDate date = null;
        LocalTime time = LocalTime.of(0, 0);

        assertThatThrownBy(() -> new ReservationDateTime(date, time, clock))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("예약 날짜, 시간이 비어있습니다.");
    }

    @Test
    void 예약을_생성할_때_예약_시간이_null이면_예외가_발생한다() {
        LocalDate date = LocalDate.of(2024, 4, 18);
        LocalTime time = null;

        assertThatThrownBy(() -> new ReservationDateTime(date, time, clock))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("예약 날짜, 시간이 비어있습니다.");
    }

    @Test
    void 예약을_1일_전에_하지_않으면_예외가_발생한다() {
        LocalDate date = LocalDate.of(2024, 4, 18);
        LocalTime time = LocalTime.of(23, 59);

        assertThatThrownBy(() -> new ReservationDateTime(date, time, clock))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("최소 1일 전 예약해야 합니다.");
    }

    @Test
    void 예약을_1일_전에_했으면_예외가_발생하지_않는다() {
        LocalDate date = LocalDate.of(2024, 4, 19);
        LocalTime time = LocalTime.of(0, 0);

        assertThatCode(() -> new ReservationDateTime(date, time, clock))
                .doesNotThrowAnyException();
    }
}
