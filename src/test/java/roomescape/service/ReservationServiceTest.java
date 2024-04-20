package roomescape.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.controller.dto.ReservationRequest;
import roomescape.controller.dto.ReservationResponse;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.repository.InMemoryReservationRepository;
import roomescape.repository.InMemoryReservationTimeRepository;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationTimeRepository;
import roomescape.service.dto.ReservationDto;
import roomescape.service.dto.ReservationTimeDto;

class ReservationServiceTest {

    private ReservationRepository reservationRepository;
    private ReservationTimeRepository reservationTimeRepository;
    private ReservationService reservationService;

    @BeforeEach
    void setUp() {
        reservationRepository = new InMemoryReservationRepository();
        reservationTimeRepository = new InMemoryReservationTimeRepository(
                Map.of(1L, new ReservationTime(1L, "13:00"))
        );
        reservationService = new ReservationService(reservationRepository, reservationTimeRepository);
    }

    @Test
    @DisplayName("예약을 추가한다.")
    void scheduleReservationTest() {
        // given
        ReservationRequest request = new ReservationRequest("브라운", "2023-08-05", 1L);
        // when
        ReservationResponse response = reservationService.scheduleReservation(request);
        // then
        List<Reservation> reservations = reservationRepository.findAll();
        assertThat(response.id()).isEqualTo(reservations.get(0).getId());
    }

    @Test
    @DisplayName("모든 예약을 조회한다.")
    void getAllReservationsTest() {
        // given
        List<ReservationDto> reservations = List.of(
                new ReservationDto("웨지", "2024-04-17", new ReservationTimeDto(1L, "13:00")),
                new ReservationDto("아루", "2023-04-18", new ReservationTimeDto(1L, "13:00")),
                new ReservationDto("브리", "2023-04-19", new ReservationTimeDto(1L, "13:00"))
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
                new ReservationDto("웨지", "2024-04-17", new ReservationTimeDto(1L, "13:00")));
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
