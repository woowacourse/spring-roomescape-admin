package roomescape.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;

public class ReservationDateTimeTest {

    @Test
    void 예약_일시는_현재일시_이전이면_예외가_발생한다() {
        // given & when & then
        assertThatThrownBy(() -> ReservationDateTime.createNewReservationTime(
                LocalDate.of(2024, 12, 31),
                new ReservationTime(1L, LocalTime.of(23, 59, 59)),
                LocalDateTime.of(2025, 1, 1, 0, 0)
        )).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 예약_일시는_현재일시_이후여야_한다() {
        // given & when & then
        assertThatCode(() -> ReservationDateTime.createNewReservationTime(
                LocalDate.of(2025, 2, 2),
                new ReservationTime(1L, LocalTime.of(10, 10, 10)),
                LocalDateTime.of(2025, 1, 1, 0, 0)
        )).doesNotThrowAnyException();
    }
}
