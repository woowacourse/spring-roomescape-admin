package roomescape.domain.Reservation;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.domain.ReservationTime.InMemoryReservationTimes;
import roomescape.domain.ReservationTime.ReservationTimes;
import roomescape.dto.request.ReservationCreateRequest;
import roomescape.dto.request.ReservationTimeCreateRequest;

class InMemoryReservationsTest {

    private final ReservationTimes reservationTimes = new InMemoryReservationTimes(new ArrayList<>());
    private final Reservations reservations = new InMemoryReservations(new ArrayList<>(), reservationTimes);

    @DisplayName("예약을 조회한다.")
    @Test
    void getTest() {

        // given
        reservationTimes.create(new ReservationTimeCreateRequest(LocalTime.of(10, 0)));
        reservations.create(new ReservationCreateRequest("체체", LocalDate.now().plusDays(1), 1L));

        // when

        // then
        assertThat(reservations.findAll().size()).isEqualTo(1);
    }

    @DisplayName("예약을 추가한다.")
    @Test
    void addTest() {

        // given
        reservationTimes.create(new ReservationTimeCreateRequest(LocalTime.of(10, 0)));

        // when
        reservations.create(new ReservationCreateRequest("체체", LocalDate.now().plusDays(1), 1L));

        // then
        assertThat(reservations.findAll().size()).isEqualTo(1);
    }

    @DisplayName("예약을 삭제한다.")
    @Test
    void deleteTest() {

        // given
        reservationTimes.create(new ReservationTimeCreateRequest(LocalTime.of(10, 0)));
        reservations.create(new ReservationCreateRequest("체체", LocalDate.now().plusDays(1), 1L));

        // when
        List<Reservation> reservations = this.reservations.findAll();
        Reservation findReservation = reservations.getFirst();
        this.reservations.delete(findReservation.getId());

        // then
        assertThat(this.reservations.findAll().size()).isEqualTo(0);
    }

}
