package roomescape.reservation.dto;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;
import roomescape.reservation.Reservation;
import roomescape.reservation.dto.request.ReservationRequest;

class ReservationRequestTest {

    @Test
    void toDomain_변환() {
        ReservationRequest request = new ReservationRequest("브라운", LocalDate.of(2023, 8, 5), LocalTime.of(15, 40));

        Reservation reservation = request.toDomain(1L);

        assertThat(reservation.getId()).isEqualTo(1L);
        assertThat(reservation.getName()).isEqualTo("브라운");
        assertThat(reservation.getDate()).isEqualTo(LocalDate.of(2023, 8, 5));
        assertThat(reservation.getTime()).isEqualTo(LocalTime.of(15, 40));
    }

    @Test
    void toDomain_id가_주입된다() {
        ReservationRequest request = new ReservationRequest("브라운", LocalDate.of(2023, 8, 5), LocalTime.of(15, 40));

        Reservation first = request.toDomain(1L);
        Reservation second = request.toDomain(2L);

        assertThat(first.getId()).isEqualTo(1L);
        assertThat(second.getId()).isEqualTo(2L);
    }
}