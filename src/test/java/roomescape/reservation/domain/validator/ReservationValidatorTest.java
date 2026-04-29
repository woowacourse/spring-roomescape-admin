package roomescape.reservation.domain.validator;

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
import roomescape.reservation.domain.exception.ReservationNotFoundException;
import roomescape.reservation.presentation.dto.ReservationRequest;
import roomescape.reservation.presentation.dto.ReservationResponse;
import roomescape.time.application.ReservationTimeService;
import roomescape.time.presentation.dto.ReservationTimeRequest;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationValidatorTest {

    @Autowired
    private ReservationValidator validator;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ReservationTimeService reservationTimeService;

    @Test
    @DisplayName("입력받은 ID에 해당하는 예약이 없으면 예외를 던진다.")
    void validateTestWhenHasNotReservation() {
        Assertions.assertThatThrownBy(() -> validator.validateDeletable(1L))
                .isInstanceOf(ReservationNotFoundException.class);
    }

    @Test
    @DisplayName("입력받은 ID에 해당하는 예약이 있으면 통과한다.")
    void validateTestWhenHasReservation() {
        reservationTimeService.addReservationTime(new ReservationTimeRequest(LocalTime.of(10,0)));
        ReservationResponse response = reservationService.addReservation(
                new ReservationRequest("브라운", LocalDate.of(2023, 8, 5), 1L));
        Assertions.assertThatCode(() -> validator.validateDeletable(response.id()))
                .doesNotThrowAnyException();
    }
}
