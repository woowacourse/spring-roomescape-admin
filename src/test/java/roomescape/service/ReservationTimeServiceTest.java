package roomescape.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import roomescape.fake.FakeReservationTimeDao;
import roomescape.dao.ReservationTimeDao;
import roomescape.dto.ReservationTimeRequest;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ReservationTimeServiceTest {

    private ReservationTimeService reservationTimeService;

    @BeforeEach
    void setUp() {
        ReservationTimeDao reservationTimeDao = new FakeReservationTimeDao();
        reservationTimeService = new ReservationTimeService(reservationTimeDao);
    }

    @Test
    void 모든_예약시간을_조회한다() {
        assertThat(reservationTimeService.getAllReservationTimes()).hasSize(2);
    }

    @Test
    void 예약시간을_추가하면_추가한_예약시간을_반환한다() {
        ReservationTimeRequest reservationTimeRequest = new ReservationTimeRequest(LocalTime.now());
        assertThat(reservationTimeService.createReservationTime(reservationTimeRequest)).isNotNull();
    }

    @Test
    void 특정_예약시간을_삭제하면_true를_반환한다() {
        assertThat(reservationTimeService.deleteReservationTimeById(1L)).isTrue();
    }

    @Test
    void 특정_예약시간을_삭제했을때_예약시간이_없으면_false를_반환한다() {
        assertThat(reservationTimeService.deleteReservationTimeById(4L)).isFalse();
    }
}