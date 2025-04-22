package roomescape.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.domain.Reservation.InMemoryReservations;
import roomescape.domain.Reservation.Reservation;
import roomescape.domain.Reservation.Reservations;
import roomescape.dto.request.ReservationCreateRequest;

class InMemoryReservationsTest {

    private final Reservations reservations = new InMemoryReservations(new ArrayList<>());

    @DisplayName("예약을 조회한다.")
    @Test
    void getTest() {

        // given
        reservations.create(new ReservationCreateRequest("체체", LocalDate.now(), LocalTime.now().plusHours(1)));

        // when

        // then
        assertThat(reservations.findAll().size()).isEqualTo(1);
    }

    @DisplayName("예약을 추가한다.")
    @Test
    void addTest() {

        // given

        // when
        reservations.create(new ReservationCreateRequest("체체", LocalDate.now(), LocalTime.now().plusHours(1)));

        // then
        assertThat(reservations.findAll().size()).isEqualTo(1);
    }

    @DisplayName("예약을 삭제한다.")
    @Test
    void deleteTest() {

        // given

        // when
        reservations.create(new ReservationCreateRequest("체체", LocalDate.now(), LocalTime.now().plusHours(1)));
        List<Reservation> reservations = this.reservations.findAll();
        Reservation findReservation = reservations.getFirst();
        this.reservations.delete(findReservation.getId());

        // then
        assertThat(this.reservations.findAll().size()).isEqualTo(0);
    }

}
