package roomescape.dto.response;

import org.junit.jupiter.api.Test;
import roomescape.Reservation;
import roomescape.dto.request.ReservationCreateRequest;

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
                LocalTime.of(10, 0)
        );

        final ReservationResponse result = ReservationResponse.from(reservation);

        assertThat(result.id()).isEqualTo(1L);
        assertThat(result.name()).isEqualTo("dompoo");
        assertThat(result.date()).isEqualTo(LocalDate.of(2025, 5, 17));
        assertThat(result.time()).isEqualTo("10:00");
    }

    @Test
    void 생성_요청으로부터_생성될_수_있다() {
        final ReservationCreateRequest request = new ReservationCreateRequest(
                "dompoo",
                LocalDate.of(2025, 5, 17),
                LocalTime.of(10, 0)
        );

        final ReservationResponse result = ReservationResponse.from(request, 1L);

        assertThat(result.id()).isEqualTo(1L);
        assertThat(result.name()).isEqualTo("dompoo");
        assertThat(result.date()).isEqualTo(LocalDate.of(2025, 5, 17));
        assertThat(result.time()).isEqualTo("10:00");
    }
}
