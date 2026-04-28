package roomescape.reservation.dto;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;
import roomescape.reservation.Reservation;
import roomescape.reservation.dto.response.ReservationResponse;

class ReservationResponseTest {

    @Test
    void from_변환() {
        Reservation reservation = new Reservation(1L, "브라운", LocalDate.of(2023, 8, 5), LocalTime.of(15, 40));

        ReservationResponse response = ReservationResponse.from(reservation);

        assertThat(response.id()).isEqualTo(1L);
        assertThat(response.name()).isEqualTo("브라운");
        assertThat(response.date()).isEqualTo(LocalDate.of(2023, 8, 5));
        assertThat(response.time()).isEqualTo(LocalTime.of(15, 40));
    }

    @Test
    void from_도메인_필드가_모두_응답에_포함된다() {
        Reservation reservation = new Reservation(42L, "제이", LocalDate.of(2024, 1, 1), LocalTime.of(10, 0));

        ReservationResponse response = ReservationResponse.from(reservation);

        assertThat(response.id()).isEqualTo(reservation.getId());
        assertThat(response.name()).isEqualTo(reservation.getName());
        assertThat(response.date()).isEqualTo(reservation.getDate());
        assertThat(response.time()).isEqualTo(reservation.getTime());
    }
}