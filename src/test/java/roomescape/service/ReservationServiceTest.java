package roomescape.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.service.dto.ReservationCreateCommand;

class ReservationServiceTest {

    @Test
    @DisplayName("생성 시점의 기준 시간보다 과거의 날짜로 예약을 생성하면 예외가 발생한다.")
    void createReservation_PastDate() {
        // given
        Clock fixedClock = Clock.fixed(
                Instant.parse("2026-05-03T00:00:00Z"),
                ZoneId.of("Asia/Seoul")
        );
        ReservationService reservationService = new ReservationService(null, null, fixedClock);
        ReservationCreateCommand command = new ReservationCreateCommand("브라운", LocalDate.of(2026, 5, 2), 1L);

        // when & then
        assertThatThrownBy(() -> reservationService.createReservation(command))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("과거 날짜는 예약할 수 없습니다.");
    }

}
