package roomescape.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.controller.dto.ReservationRequest;
import roomescape.controller.dto.ReservationResponse;
import roomescape.domain.Reservation;
import roomescape.repository.ReservationInMemoryRepository;
import roomescape.repository.ReservationRepository;
import roomescape.service.dto.ReservationDto;

class ReservationServiceTest {

    private final ReservationRepository reservationRepository = new ReservationInMemoryRepository();
    private final ReservationService reservationService = new ReservationService(reservationRepository);

    @BeforeEach
    void setUp() {
        reservationRepository.deleteAll();
    }

    @Test
    @DisplayName("예약을 추가한다.")
    void scheduleReservationTest() {
        // given
        ReservationRequest request = new ReservationRequest("브라운", "2023-08-05", "15:40");
        // when
        reservationService.scheduleReservation(request);
        List<Reservation> actual = reservationRepository.findAll();
        // then
        assertThat(actual).hasSize(1);
    }

    @Test
    @DisplayName("모든 예약을 조회한다.")
    void getAllReservationsTest() {
        // given
        List<ReservationDto> reservations = List.of(
                new ReservationDto("웨지", "2024-04-17", "15:00"),
                new ReservationDto("아루", "2023-04-18", "13:00"),
                new ReservationDto("브리", "2023-04-19", "16:00")
        );
        reservations.forEach(reservationRepository::addReservation);
        // when
        List<ReservationResponse> actual = reservationService.getAllReservations();
        // then
        assertThat(actual).hasSize(3);
    }

    @Test
    @DisplayName("취소할 때, id에 해당하는 예약이 존재한다면 성공적으로 취소한다.")
    void cancelReservationTest() {
        // given
        Reservation reservation = reservationRepository.addReservation(
                new ReservationDto("웨지", "2024-04-17", "15:00"));
        // when
        reservationService.cancelReservation(reservation.getId());
        List<Reservation> actual = reservationRepository.findAll();
        // then
        assertThat(actual).isEmpty();
    }

    @Test
    @DisplayName("취소할 때, id에 해당하는 예약이 존재하지 않는다면 예외를 발생한다.")
    void cancelReservationNotFoundTest() {
        assertThatThrownBy(() -> reservationService.cancelReservation(1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("id에 해당하는 예약을 찾을 수 없습니다.");
    }
}
