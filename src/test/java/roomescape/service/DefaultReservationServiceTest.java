package roomescape.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import roomescape.reservation.application.DefaultReservationService;
import roomescape.reservation.domain.ReservationId;

import java.util.NoSuchElementException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@SpringBootTest
class DefaultReservationServiceTest {

    @Autowired
    private DefaultReservationService reservationService;

    @Test
    @DisplayName("존재하지 않는 아이디를 삭제시 예외 발생")
    void deleteException() {
        // given
        final long invalidId = 1;

        // when & then
        assertThatThrownBy(() -> reservationService.delete(ReservationId.from(invalidId)))
                .isInstanceOf(NoSuchElementException.class);
    }
}
