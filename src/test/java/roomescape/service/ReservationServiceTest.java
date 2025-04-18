package roomescape.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.domain.Reservation;
import roomescape.repository.ReservationRepository;

class ReservationServiceTest {

    private ReservationRepository reservationRepository;
    private ReservationService reservationService;

    @BeforeEach
    void setUp() {
        reservationRepository = new ReservationRepository();
        reservationService = new ReservationService(reservationRepository);
    }

    @DisplayName("예약을 조회한다.")
    @Test
    void getTest() {

        // given

        // when

        // then
        assertThat(reservationService.findAll().size()).isEqualTo(0);
    }

    @DisplayName("예약을 추가한다.")
    @Test
    void addTest() {

        // given

        // when
        reservationService.create("체체", LocalDate.now(), LocalTime.now().plusHours(1));

        // then
        assertThat(reservationService.findAll().size()).isEqualTo(1);
    }

    @DisplayName("예약을 삭제한다.")
    @Test
    void deleteTest() {

        // given

        // when
        reservationService.create("체체", LocalDate.now(), LocalTime.now().plusHours(1));
        List<Reservation> reservations = reservationService.findAll();
        Reservation findReservation = reservations.getFirst();
        reservationService.delete(findReservation.getId());

        // then
        assertThat(reservationService.findAll().size()).isEqualTo(0);
    }
}
