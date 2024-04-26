package roomescape.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import roomescape.exception.reservation.time.NotExistReservationTimeException;
import roomescape.exception.reservation.time.ReservationExistInReservationTimeException;
import roomescape.service.request.ReservationCreateRequestInService;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class ReservationTimeServiceTest {
    @Autowired
    ReservationTimeService reservationTimeService;
    @Autowired
    ReservationService reservationService;

    @Test
    @DisplayName("삭제하려는 예약시간에 해당하는 예약이 존재하면 예외를 발생한다")
    void throw_exception_when_exist_reservation_in_reservation_time() {
        final var reservationTimeId = reservationTimeService.createReservationTime("10:00")
                                                            .id();

        reservationService.createReservation(new ReservationCreateRequestInService("조이썬", "2023-10-03", reservationTimeId));
        assertThatThrownBy(() -> reservationTimeService.deleteReservationTime(reservationTimeId))
                .isInstanceOf(ReservationExistInReservationTimeException.class);
    }

    @Test
    @DisplayName("예약시간 id에 해당하는 값이 없으면 예외를 발생한다")
    void throw_exception_when_reservationTime_not_exist() {
        assertThatThrownBy(() -> reservationTimeService.deleteReservationTime(10000))
                .isInstanceOf(NotExistReservationTimeException.class);
    }
}
