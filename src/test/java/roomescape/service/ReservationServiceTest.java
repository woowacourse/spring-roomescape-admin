package roomescape.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.controller.request.ReservationRequest;
import roomescape.service.exception.ReservationNotFoundException;
import roomescape.service.exception.ReservationTimeNotFoundException;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationServiceTest {

    @Autowired
    private ReservationService reservationService;

    @DisplayName("존재하지 않은 reservationTime으로 reservation 저장시에 예외를 발생한다.")
    @Test
    void createWithNonExistTime() {
        // given
        final long nonExistTimeId = 1L;
        final ReservationRequest request = new ReservationRequest("엠제이", LocalDate.now().plusDays(1), nonExistTimeId);

        // when & then
        assertThatThrownBy(() -> reservationService.create(request))
                .isInstanceOf(ReservationTimeNotFoundException.class);
    }

    @DisplayName("존재하지 않은 reservation을 삭제하면 예외를 발생한다.")
    @Test
    void deleteNonExistReservation() {
        // given
        final long nonExistReservationId = 1;

        // when & then
        assertThatThrownBy(() -> reservationService.deleteById(nonExistReservationId))
                .isInstanceOf(ReservationNotFoundException.class);
    }
}
