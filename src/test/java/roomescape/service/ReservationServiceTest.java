package roomescape.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import roomescape.controller.dto.ReservationRequest;
import roomescape.controller.dto.ReservationResponse;
import roomescape.domain.Reservation;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationTimeRepository;
import roomescape.service.dto.ReservationCreationDto;
import roomescape.service.dto.ReservationTimeDto;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class ReservationServiceTest {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ReservationTimeRepository reservationTimeRepository;

    @Autowired
    private ReservationService reservationService;

    @AfterEach
    void tearDown() {
        reservationRepository.deleteAll();
        reservationTimeRepository.deleteAll();
    }

    @Test
    @DisplayName("예약을 추가한다.")
    void scheduleReservationTest() {
        // given
        Long timeId = createTimeAndReturnId();
        ReservationRequest request = new ReservationRequest("브라운", "2023-08-05", timeId);
        // when
        ReservationResponse response = reservationService.scheduleReservation(request);
        // then
        List<Reservation> reservations = reservationRepository.findAll();
        assertThat(response.id()).isEqualTo(reservations.get(0).getId());
    }

    @Test
    @DisplayName("예약을 추가할 때, 시간이 존재하지 않는다면 예외를 발생한다.")
    void cancelReservationNotFoundTest() {
        // given
        ReservationRequest request = new ReservationRequest("브라운", "2023-08-05", 999L);
        // when, then
        assertThatThrownBy(() -> reservationService.scheduleReservation(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("예약 시간이 존재하지 않습니다.");

    }

    @Test
    @DisplayName("모든 예약을 조회한다.")
    void getAllReservationsTest() {
        // given
        Long timeId = createTimeAndReturnId();
        List<ReservationCreationDto> reservations = List.of(
                new ReservationCreationDto("웨지", "2024-04-17", new ReservationTimeDto(timeId, "13:00")),
                new ReservationCreationDto("아루", "2023-04-18", new ReservationTimeDto(timeId, "13:00")),
                new ReservationCreationDto("브리", "2023-04-19", new ReservationTimeDto(timeId, "13:00"))
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
        Long timeId = createTimeAndReturnId();
        Reservation reservation = reservationRepository.addReservation(
                new ReservationCreationDto("웨지", "2024-04-17", new ReservationTimeDto(timeId, "13:00")));
        // when
        reservationService.cancelReservation(reservation.getId());
        List<Reservation> actual = reservationRepository.findAll();
        // then
        assertThat(actual).isEmpty();
    }


    private Long createTimeAndReturnId() {
        ReservationTimeDto dto = new ReservationTimeDto(1L, "13:00");
        return reservationTimeRepository.create(dto)
                .getId();
    }
}
