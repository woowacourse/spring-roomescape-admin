package roomescape.reservation.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.dto.ReservationRequest;
import roomescape.reservation.repository.FakeReservationRepository;
import roomescape.reservation.repository.ReservationRepository;
import roomescape.reservationtime.domain.ReservationTime;
import roomescape.reservationtime.repository.FakeReservationTimeRepository;
import roomescape.reservationtime.repository.ReservationTimeRepository;

class ReservationServiceTest {

    private ReservationRepository reservationRepository;
    private ReservationTimeRepository reservationTimeRepository;
    private ReservationService reservationService;

    @BeforeEach
    void beforeEach() {
        reservationRepository = new FakeReservationRepository();
        reservationTimeRepository = new FakeReservationTimeRepository();
        reservationService = new ReservationService(reservationRepository, reservationTimeRepository);
    }

    @Test
    @DisplayName("모든 예약을 조회한다.")
    void getReservations() {
        // given
        ReservationTime reservationTime = new ReservationTime(
                1L,
                LocalTime.of(10, 0)
        );
        Reservation reservation = new Reservation(
                1L,
                "브라운",
                LocalDate.of(2023, 8, 5),
                reservationTime
        );
        reservationRepository.save(reservation);

        // when
        List<Reservation> reservations = reservationService.getReservations();

        // then
        assertThat(reservations.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("예약을 저장한다.")
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
        Reservation created = reservationService.createReservation(request);

        // then
        Reservation expected = new Reservation(
                1L,
                "미소",
                LocalDate.of(2025, 4, 21),
                new ReservationTime(
                        1L,
                        LocalTime.of(10, 0)
                )
        );
        assertThat(created).isEqualTo(expected);
    }

    @Test
    @DisplayName("id로 예약을 삭제한다.")
    void deleteReservation() {
        // given
        ReservationTime reservationTime = new ReservationTime(
                1L,
                LocalTime.of(10, 0)
        );
        Reservation reservation = new Reservation(
                1L,
                "브라운",
                LocalDate.of(2023, 8, 5),
                reservationTime
        );
        reservationRepository.save(reservation);

        // when
        reservationService.deleteReservation(1L);

        // then
        List<Reservation> reservations = reservationRepository.findAll();
        assertThat(reservations.size()).isEqualTo(0);
    }
}
