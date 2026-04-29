package roomescape.reservation.application;

import java.time.LocalDate;
import java.time.LocalTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import roomescape.reservation.domain.exception.ReservationNotFoundException;
import roomescape.reservation.presentation.dto.ReservationRequest;
import roomescape.reservation.presentation.dto.ReservationResponse;
import roomescape.time.application.ReservationTimeService;
import roomescape.time.domain.exception.ReservationTimeNotFoundException;
import roomescape.time.presentation.dto.ReservationTimeRequest;
import roomescape.time.presentation.dto.ReservationTimeResponse;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationServiceTest {

    @Autowired
    ReservationService reservationService;

    @Autowired
    ReservationTimeService timeService;

    @Test
    @DisplayName("존재하는 시간 ID를 가지고 예약 생성을 하면 정상 저장된다.")
    void normalTest() {
        ReservationTimeResponse response = timeService.addReservationTime(
                new ReservationTimeRequest(LocalTime.of(10, 0)));
        ReservationResponse reservation = reservationService.addReservation(new ReservationRequest(
                "브라운",
                LocalDate.of(2026, 4, 29),
                response.id()
        ));
        Assertions.assertThat(reservationService.getReservations().getFirst()).isEqualTo(reservation);
    }

    @Test
    @DisplayName("존재하는 예약을 지우면 정상적으로 지워진다.")
    void normalCancel() {
        ReservationTimeResponse response = timeService.addReservationTime(
                new ReservationTimeRequest(LocalTime.of(10, 0)));
        ReservationResponse reservation = reservationService.addReservation(new ReservationRequest(
                "브라운",
                LocalDate.of(2026, 4, 29),
                response.id()
        ));
        reservationService.cancelReservation(reservation.id());
        Assertions.assertThat(reservationService.getReservations().size()).isZero();
    }

    @Test
    @DisplayName("예약 생성시 존재하지 않는 시간ID를 입력하면 예외를 던진다.")
    void wrongTimeTest() {
        Assertions.assertThatThrownBy(() -> reservationService.addReservation(
                new ReservationRequest(
                        "브라운",
                        LocalDate.of(2026, 4, 29),
                        1L
                )
            )
        ).isInstanceOf(ReservationTimeNotFoundException.class);
    }

    @Test
    @DisplayName("존재하지 않는 예약ID를 입력하면 예외를 던진다.")
    void wrongReservationId() {
        Assertions.assertThatThrownBy(() -> reservationService.cancelReservation(1L))
                .isInstanceOf(ReservationNotFoundException.class);
    }
}
