package roomescape.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.repository.ReservationRepository;
import roomescape.service.ReservationService;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ReservationControllerTest {
    private final ReservationRepository reservationRepository = new ReservationRepository();
    private final ReservationService reservationService = new ReservationService(reservationRepository);
    private final ReservationController reservationController = new ReservationController(reservationService);

    @Test
    @DisplayName("존재하지 않는 예약 ID로 삭제를 요청할 경우 예외가 발생한다.")
    void deleteReservation() {
        // given
        Long reservationId = 1L;

        // when & then
        assertThatThrownBy(() -> reservationController.deleteReservation(reservationId))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
