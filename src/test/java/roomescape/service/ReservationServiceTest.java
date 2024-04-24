package roomescape.service;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static roomescape.fixture.DateTimeFixture.DATE_2024_04_20;
import static roomescape.fixture.DateTimeFixture.TIME_03_00;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import roomescape.entity.Reservation;
import roomescape.entity.ReservationTime;

@SpringBootTest
@Transactional
@Rollback
class ReservationServiceTest {
    @Autowired
    private ReservationService reservationService;
    @Autowired
    private TimeService timeService;

    private ReservationTime time_03_00;

    @BeforeEach
    void setUp() {
        time_03_00 = timeService.save(TIME_03_00);
    }

    @DisplayName("시간이 겹치는 예약이 존재하지 않는 경우 예약에 성공한다")
    @Test
    void reservationSaveSuccessTest() {
        Reservation reservation = new Reservation("리비", DATE_2024_04_20, time_03_00);

        assertThatCode(() -> reservationService.saveReservation(reservation))
                .doesNotThrowAnyException();
    }

    @DisplayName("시간이 겹치는 예약이 존재할 경우 예약에 실패한다")
    @Test
    void reservationSaveFailByTimeConflictTest() {
        Reservation reservation = new Reservation("리비", DATE_2024_04_20, time_03_00);
        Reservation conflictReservation = new Reservation("웨지", DATE_2024_04_20, time_03_00);
        reservationService.saveReservation(reservation);

        assertThatThrownBy(() -> reservationService.saveReservation(conflictReservation))
                .isInstanceOf(IllegalStateException.class);
    }
}
