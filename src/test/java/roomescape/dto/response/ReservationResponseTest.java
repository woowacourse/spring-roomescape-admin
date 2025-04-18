package roomescape.dto.response;


import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.model.Reservation;

class ReservationResponseTest {

    @Test
    @DisplayName("Reservation dto 변환 테스트")
    void changeReservationToDtoTest() {
        // given
        Reservation reservation = Reservation.createReservation(1L, "a", LocalDateTime.of(2000, 11, 2, 11, 0));
        // when
        ReservationResponse response = ReservationResponse.from(reservation);
        // then
        assertThat(response.id()).isEqualTo(1L);
        assertThat(response.name()).isEqualTo("a");
        assertThat(response.date()).isEqualTo("2000-11-02");
        assertThat(response.time()).isEqualTo("11:00");
    }

    @Test
    @DisplayName("Reservations dto 변환 테스트")
    void changeReservationsToDtosTest() {
        // given
        List<Reservation> reservations = List.of(
                Reservation.createReservation(1L, "a", LocalDateTime.of(2000, 11, 2, 11, 0)),
                Reservation.createReservation(2L, "b", LocalDateTime.of(2000, 11, 3, 12, 0))
        );
        // when
        List<ReservationResponse> responses = ReservationResponse.from(reservations);
        // then
        assertThat(responses).hasSize(2);
        ReservationResponse firstReservation = responses.get(0);
        ReservationResponse secondReservation = responses.get(1);
        assertThat(firstReservation.id()).isEqualTo(1L);
        assertThat(firstReservation.name()).isEqualTo("a");
        assertThat(firstReservation.date()).isEqualTo("2000-11-02");
        assertThat(firstReservation.time()).isEqualTo("11:00");
        assertThat(secondReservation.id()).isEqualTo(2L);
        assertThat(secondReservation.name()).isEqualTo("b");
        assertThat(secondReservation.date()).isEqualTo("2000-11-03");
        assertThat(secondReservation.time()).isEqualTo("12:00");
    }

}