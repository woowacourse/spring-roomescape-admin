package roomescape.service;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static roomescape.fixture.DateTimeFixture.DAY_AFTER_TOMORROW;
import static roomescape.fixture.DateTimeFixture.GAME_TIME_WITH_ID_0300;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import roomescape.entity.GameTime;
import roomescape.entity.Reservation;

@SpringBootTest
@Transactional
@Rollback
class ReservationServiceTest {
    @Autowired
    private ReservationService reservationService;
    @Autowired
    private GameTimeService gameTimeService;

    private GameTime time_03_00;

    @BeforeEach
    void setUp() {
        time_03_00 = gameTimeService.save(GAME_TIME_WITH_ID_0300);
    }

    @DisplayName("시간이 겹치는 예약이 존재하지 않는 경우 예약에 성공한다")
    @Test
    void reservationSaveSuccessTest() {
        Reservation reservation = new Reservation("리비", DAY_AFTER_TOMORROW, time_03_00);

        assertThatCode(() -> reservationService.saveReservation(reservation))
                .doesNotThrowAnyException();
    }

    @DisplayName("시간이 겹치는 예약이 존재할 경우 예약에 실패한다")
    @Test
    void reservationSaveFailByTimeConflictTest() {
        Reservation reservation = new Reservation("리비", DAY_AFTER_TOMORROW, time_03_00);
        Reservation conflictReservation = new Reservation("웨지", DAY_AFTER_TOMORROW, time_03_00);
        reservationService.saveReservation(reservation);

        assertThatThrownBy(() -> reservationService.saveReservation(conflictReservation))
                .isInstanceOf(IllegalStateException.class);
    }
}
