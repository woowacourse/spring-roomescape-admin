package roomescape.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import roomescape.entity.ReservationTime;
import roomescape.fake.FakeReservationTimeDao;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class FakeReservationTimeDaoTest {

    private ReservationTimeDao reservationTimeDao;

    @BeforeEach
    void setUp() {
        reservationTimeDao = new FakeReservationTimeDao();
    }

    @Test
    void 모든_예약시간을_조회한다() {
        assertThat(reservationTimeDao.findAll()).hasSize(2);
    }

    @Test
    void 예약시간을_추가하면_추가한_예약시간을_반환한다() {
        ReservationTime newReservationTime = ReservationTime.of(LocalTime.of(10, 0));
        assertThat(reservationTimeDao.insert(newReservationTime)).isNotNull();
    }

    @Test
    void 특정_예약시간을_삭제하면_true를_반환한다() {
        assertThat(reservationTimeDao.deleteById(1L)).isTrue();
    }

    @Test
    void 특정_예약시간을_삭제했을때_예약시간이_없으면_false를_반환한다() {
        assertThat(reservationTimeDao.deleteById(4L)).isFalse();
    }
}
