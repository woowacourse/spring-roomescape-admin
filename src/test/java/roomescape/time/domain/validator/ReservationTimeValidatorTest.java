package roomescape.time.domain.validator;

import static org.springframework.boot.test.context.SpringBootTest.*;
import static org.springframework.test.annotation.DirtiesContext.*;

import java.time.LocalDate;
import java.time.LocalTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.reservation.application.ReservationService;
import roomescape.reservation.presentation.dto.ReservationRequest;
import roomescape.time.application.ReservationTimeService;
import roomescape.time.domain.exception.ReservationTimeInUseException;
import roomescape.time.domain.exception.ReservationTimeNotFoundException;
import roomescape.time.presentation.dto.ReservationTimeRequest;
import roomescape.time.presentation.dto.ReservationTimeResponse;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationTimeValidatorTest {

    @Autowired
    private ReservationTimeValidator validator;

    @Autowired
    private ReservationTimeService timeService;

    @Autowired
    private ReservationService reservationService;

    @Test
    @DisplayName("입력받은 ID에 해당하는 시간이 없으면 예외를 던진다.")
    void validateTestHasNotTime() {
        Assertions.assertThatThrownBy(() -> validator.validateDeletable(1L))
                .isInstanceOf(ReservationTimeNotFoundException.class);
    }

    @Test
    @DisplayName("입력받은 ID에 해당하는 시간이 있으면 통과한다.")
    void validateTestHasTime() {
        ReservationTimeResponse response = timeService.addReservationTime(
                new ReservationTimeRequest(LocalTime.of(10, 0)));
        Assertions.assertThatCode(() -> validator.validateDeletable(response.id()))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("예약과 연결된 시간을 지우려고 하면 예외를 던진다.")
    void validateConnectedWithReservation() {
        ReservationTimeResponse response = timeService.addReservationTime(
                new ReservationTimeRequest(LocalTime.of(10, 0)));
        reservationService.addReservation(
                new ReservationRequest("브라운", LocalDate.of(2026, 5, 20), response.id()));
        Assertions.assertThatThrownBy(() -> timeService.deleteReservationTime(response.id()))
                .isInstanceOf(ReservationTimeInUseException.class);
    }
}
