package roomescape.unit.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import roomescape.domain.Reservation;
import roomescape.service.ReservationService;
import roomescape.unit.repository.FakeReservationRepository;


@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ReservationServiceTest {

    static ReservationService reservationService;

    @BeforeEach
    void setup() {
        reservationService = new ReservationService(new FakeReservationRepository());
    }

    @Test
    void 예약을_추가하고_조회할_수_있다() {
        assertThat(reservationService.allReservations().size()).isEqualTo(0);
        reservationService.addReservation(
                new Reservation(null, "praisebak", LocalDate.now().plusDays(1L), LocalTime.now()));
        assertThat(reservationService.allReservations().size()).isEqualTo(1);
    }

    @Test
    void 예약을_삭제하고_조회할_수_있다() {
        long id = reservationService.addReservation(
                new Reservation(null, "praisebak", LocalDate.now().plusDays(1L), LocalTime.now()));
        assertThat(reservationService.allReservations().size()).isEqualTo(1);
        reservationService.deleteReservation(id);
        assertThat(reservationService.allReservations().size()).isEqualTo(0);
    }
}
