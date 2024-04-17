package roomescape.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AdminControllerTest {
    private final AdminController adminController = new AdminController();

    @Test
    @DisplayName("존재하지 않는 예약 ID로 삭제를 요청할 경우 예외가 발생한다.")
    void deleteReservation() {
        // given
        Long reservationId = 1L;

        // when & then
        assertThatThrownBy(() -> adminController.deleteReservation(reservationId))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
