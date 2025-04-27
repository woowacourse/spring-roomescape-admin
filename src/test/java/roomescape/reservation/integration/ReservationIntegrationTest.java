package roomescape.reservation.integration;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.dto.ReservationRequest;
import roomescape.reservation.repository.ReservationRepository;
import roomescape.reservation.service.ReservationService;
import roomescape.reservationtime.domain.ReservationTime;
import roomescape.reservationtime.repository.ReservationTimeRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationIntegrationTest {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ReservationTimeRepository reservationTimeRepository;

    @Test
    @DisplayName("모든 예약을 DB에서 조회한다.")
    void getReservations() {
        // given
        ReservationTime reservationTime = new ReservationTime(
                1L,
                LocalTime.of(10, 0)
        );
        Reservation reservation = new Reservation(
                1L,
                "미소",
                LocalDate.of(2025, 4, 21),
                reservationTime
        );
        reservationTimeRepository.save(reservationTime);
        reservationRepository.save(reservation);

        // when
        List<Reservation> reservations = reservationService.getReservations();

        // then
        assertThat(reservations.getFirst()).isEqualTo(reservation);
    }

    @Test
    @DisplayName("예약을 DB에 저장한다.")
    void createReservation() {
        // given
        ReservationTime reservationTime = new ReservationTime(
                1L,
                LocalTime.of(10, 0)
        );
        reservationTimeRepository.save(reservationTime);
        ReservationRequest request = new ReservationRequest(
                LocalDate.of(2025, 4, 21),
                "미소",
                1L
        );

        // when
        Reservation saved = reservationService.createReservation(request);

        // then
        Reservation found = reservationRepository.findById(1L).get();
        assertThat(saved).isEqualTo(found);
    }

    @Test
    @DisplayName("예약을 DB에서 삭제한다.")
    void deleteReservation() {
        // given
        ReservationTime reservationTime = new ReservationTime(
                1L,
                LocalTime.of(10, 0)
        );
        Reservation reservation = new Reservation(
                1L,
                "미소",
                LocalDate.of(2025, 4, 21),
                reservationTime
        );
        reservationTimeRepository.save(reservationTime);
        reservationRepository.save(reservation);

        // when
        reservationService.deleteReservation(1L);

        // then
        List<Reservation> reservations = reservationRepository.findAll();
        assertThat(reservations.size()).isEqualTo(0);
    }
}
