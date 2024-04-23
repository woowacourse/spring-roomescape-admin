package roomescape.service;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import roomescape.entity.Reservation;

@SpringBootTest
@Transactional
@Rollback
class ReservationServiceTest {
    @Autowired
    private ReservationService reservationService;

    @DisplayName("시간이 겹치는 예약이 존재하지 않는 경우 예약에 성공한다")
    @Test
    void reservationSaveSuccessTest() {
        Reservation reservation = new Reservation("리비", LocalDate.of(2024, 4, 20), LocalTime.of(3, 57));

        assertThatCode(() -> reservationService.saveReservation(reservation))
                .doesNotThrowAnyException();
    }

    @DisplayName("시간이 겹치는 예약이 존재할 경우 예약에 실패한다")
    @Test
    void reservationSaveFailByTimeConflictTest() {
        Reservation reservation = new Reservation("리비", LocalDate.of(2024, 4, 20), LocalTime.of(3, 57));
        Reservation conflictReservation = new Reservation("웨지", LocalDate.of(2024, 4, 20), LocalTime.of(3, 30));
        reservationService.saveReservation(reservation);

        assertThatThrownBy(() -> reservationService.saveReservation(conflictReservation))
                .isInstanceOf(IllegalStateException.class);
    }
}
