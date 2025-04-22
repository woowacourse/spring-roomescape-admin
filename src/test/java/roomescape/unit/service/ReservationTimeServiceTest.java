package roomescape.unit.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.service.ReservationTimeService;
import roomescape.unit.repository.FakeReservationTimeRepository;


@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ReservationTimeServiceTest {

    static ReservationTimeService reservationTimeService;

    @BeforeEach
    void setup() {
        reservationTimeService = new ReservationTimeService(new FakeReservationTimeRepository());
    }

    @Test
    void 예약을_추가하고_조회할_수_있다() {
        assertThat(reservationTimeService.allReservationTimes().size()).isEqualTo(0);
        reservationTimeService.addReservationTime(new ReservationTime(1, LocalTime.now()));
        reservationService.addReservation(new Reservation(null, "praisebak", LocalDate.now(), LocalTime.now()));
        assertThat(reservationService.allReservations().size()).isEqualTo(1);
    }

    @Test
    void 예약을_삭제하고_조회할_수_있다() {
        long id = reservationService.addReservation(
                new Reservation(null, "praisebak", LocalDate.now(), LocalTime.now()));
        assertThat(reservationService.allReservations().size()).isEqualTo(1);
        reservationService.deleteReservation(id);
        assertThat(reservationService.allReservations().size()).isEqualTo(0);
    }
}
