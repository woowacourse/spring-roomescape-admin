package roomescape.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import roomescape.dto.request.ReservationRequest;
import roomescape.dto.request.ReservationTimeCreateRequest;
import roomescape.exception.reservation.time.NotExistReservationTimeException;
import roomescape.exception.reservation.time.ReservationExistInReservationTimeException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class ReservationTimeServiceTest {
    @Autowired
    ReservationTimeService reservationTimeService;
    @Autowired
    ReservationService reservationService;

    @Test
    @DisplayName("예약시간에 해당하는 예약이 존재하면 예외를 발생한다")
    void throw_exception_when_exist_reservation_in_reservation_time() {
        reservationTimeService.createReservationTime("10:00");

        reservationService.createReservation("비밥", "2024-10-03", 1);
        assertThatThrownBy(() -> reservationTimeService.deleteReservationTime(1))
                .isInstanceOf(ReservationExistInReservationTimeException.class);
    }

    @Test
    @DisplayName("예약시간 id에 해당하는 값이 없으면 예외를 발생한다")
    void throw_exception_when_reservationTime_not_exist() {
        assertThatThrownBy(() -> reservationTimeService.deleteReservationTime(3))
                .isInstanceOf(NotExistReservationTimeException.class);
    }
}
