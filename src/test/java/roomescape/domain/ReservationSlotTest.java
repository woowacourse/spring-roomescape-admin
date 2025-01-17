package roomescape.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.fixture.ThemeFixture;

class ReservationSlotTest {

    @Test
    @DisplayName("예약 시간이 특정 시간보다 이전이면 true를 반환한다.")
    void isBeforeTrue() {
        // given
        LocalDate date = LocalDate.now();
        LocalTime early = LocalTime.of(10, 0);
        LocalTime late = LocalTime.of(10, 1);

        ReservationTime time = new ReservationTime(early);
        ReservationSlot slot = new ReservationSlot(date, time, ThemeFixture.theme1());

        // when
        boolean result = slot.isBefore(LocalDateTime.of(date, late));

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("예약 시간이 특정 시간보다 이후이면 false를 반환한다.")
    void isBeforeFalse() {
        // given
        LocalDate date = LocalDate.now();
        LocalTime early = LocalTime.of(10, 0);
        LocalTime late = LocalTime.of(10, 1);

        ReservationTime time = new ReservationTime(late);
        ReservationSlot slot = new ReservationSlot(date, time, ThemeFixture.theme1());

        // when
        boolean result = slot.isBefore(LocalDateTime.of(date, early));

        // then
        assertThat(result).isFalse();
    }
}
