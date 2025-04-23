package roomescape.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.service.exception.ReservationTimeNotFoundException;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationTimeServiceTest {

    @Autowired
    private ReservationTimeService reservationTimeService;

    @DisplayName("존재하지 않은 reservationTime을 삭제하면 예외를 발생한다.")
    @Test
    void deleteNonExistReservationTime() {
        // given
        final long nonExistReservationTimeId = 1;

        // when & then
        assertThatThrownBy(() -> reservationTimeService.deleteById(nonExistReservationTimeId))
                .isInstanceOf(ReservationTimeNotFoundException.class);
    }
}
