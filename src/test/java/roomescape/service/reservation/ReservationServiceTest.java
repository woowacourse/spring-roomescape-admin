package roomescape.service.reservation;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.dto.reservation.request.ReservationRequestDto;
import roomescape.dto.reservation.response.ReservationResponseDto;
import roomescape.repository.ReservationRepositoryFake;

import static org.assertj.core.api.Assertions.assertThat;

class ReservationServiceTest {

    private ReservationService reservationService;

    @BeforeEach
    void init() {
        reservationService = new ReservationService(new ReservationRepositoryFake());
    }

    @DisplayName("예약 정보 삽입 테스트")
    @Test
    void insertTest() {
        ReservationRequestDto request = new ReservationRequestDto("name", "2000-09-07", "10:00");
        ReservationResponseDto response = reservationService.addReservation(request);
        assertThat(response.id()).isEqualTo(1L);
    }

    @DisplayName("예약 정보 전체 조회 테스트")
    @Test
    void findAllTest() {
        reservationService.addReservation(new ReservationRequestDto("name1", "2000-09-07", "10:00"));
        reservationService.addReservation(new ReservationRequestDto("name2", "2000-09-07", "10:00"));
        reservationService.addReservation(new ReservationRequestDto("name3", "2000-09-07", "10:00"));

        int findSize = reservationService.findAllReservation().reservations().size();
        assertThat(findSize).isEqualTo(3);
    }

    @DisplayName("예약 정보 삭제 테스트")
    @Test
    void deleteTest() {
        ReservationRequestDto request = new ReservationRequestDto("name", "2000-09-07", "10:00");
        ReservationResponseDto response = reservationService.addReservation(request);

        reservationService.deleteReservationById(response.id());
        int findSize = reservationService.findAllReservation().reservations().size();
        assertThat(findSize).isEqualTo(0);
    }

    @DisplayName("예약 정보 삭제 실패 테스트")
    @Test
    void deleteFailTest() {
        Long noneExistId = -1L;

        Assertions.assertThatThrownBy(() -> reservationService.deleteReservationById(noneExistId))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
