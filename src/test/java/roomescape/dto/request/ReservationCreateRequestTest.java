package roomescape.dto.request;

import org.junit.jupiter.api.Test;
import roomescape.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.*;

class ReservationCreateRequestTest {

    @Test
    void 도메인으로_변환할_수_있다() {
        final ReservationCreateRequest request = new ReservationCreateRequest(
                "dompoo",
                LocalDate.of(2025, 5, 17),
                LocalTime.of(10, 0)
        );

        final Reservation result = request.toDomain(1L);

        assertThat(result.id()).isEqualTo(1L);
        assertThat(result.name()).isEqualTo("dompoo");
        assertThat(result.date()).isEqualTo(LocalDate.of(2025, 5, 17));
        assertThat(result.time()).isEqualTo(LocalTime.of(10, 0));
    }
}
