package roomescape.dto.response;

import org.junit.jupiter.api.Test;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.*;

class ReservationResponseTest {

    @Test
    void 도메인으로부터_생성될_수_있다() {
        final Reservation reservation = new Reservation(
                1L,
                "dompoo",
                LocalDate.of(2025, 5, 17),
                new ReservationTime(2L, LocalTime.of(10, 0))
        );

        final ReservationResponse result = ReservationResponse.from(reservation);

        assertThat(result.id()).isEqualTo(1L);
        assertThat(result.name()).isEqualTo("dompoo");
        assertThat(result.date()).isEqualTo(LocalDate.of(2025, 5, 17));
        assertThat(result.time().id()).isEqualTo(2L);
        assertThat(result.time().startAt()).isEqualTo("10:00");
    }
}
