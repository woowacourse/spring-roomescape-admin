package roomescape.unit.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
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
        ReservationTime reservationTime = new ReservationTime(null, LocalTime.now().plusHours(1L));
        assertThat(reservationService.allReservations().size()).isEqualTo(0);
        reservationService.addReservation(
                new Reservation(null, "praisebak", LocalDate.now().plusDays(1L), reservationTime));
        assertThat(reservationService.allReservations().size()).isEqualTo(1);
    }

    @Test
    void 이전_날짜에_예약할_수_없다() {
        ReservationTime reservationTime = new ReservationTime(null, LocalTime.now());
        Assertions.assertThatThrownBy(() -> reservationService.addReservation(
                        new Reservation(null, "투다", LocalDate.now().minusDays(1), reservationTime)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 같은날짜일시_이전_시간에_예약할_수_없다() {
        ReservationTime reservationTime = new ReservationTime(null, LocalTime.now().minusHours(1));
        Assertions.assertThatThrownBy(() -> reservationService.addReservation(
                        new Reservation(null, "투다", LocalDate.now(), reservationTime)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 이후_날짜에_예약할_수_있다() {
        ReservationTime reservationTime = new ReservationTime(null, LocalTime.now());
        Assertions.assertThatCode(() -> reservationService.addReservation(
                        new Reservation(null, "투다", LocalDate.now().plusDays(1), reservationTime)))
                .doesNotThrowAnyException();
    }

    @Test
    void 같은날짜일시_이후_시간_예약할_수_있다() {
        ReservationTime reservationTime = new ReservationTime(null, LocalTime.now().plusHours(1));
        Assertions.assertThatCode(() -> reservationService.addReservation(
                        new Reservation(null, "투다", LocalDate.now(), reservationTime)))
                .doesNotThrowAnyException();
    }


    @Test
    void 예약을_삭제하고_조회할_수_있다() {
        ReservationTime reservationTime = new ReservationTime(1L, LocalTime.now().plusHours(1L));
        long id = reservationService.addReservation(
                new Reservation(null, "praisebak", LocalDate.now().plusDays(1L), reservationTime));
        assertThat(reservationService.allReservations().size()).isEqualTo(1);
        reservationService.deleteReservation(id);
        assertThat(reservationService.allReservations().size()).isEqualTo(0);
    }
}
