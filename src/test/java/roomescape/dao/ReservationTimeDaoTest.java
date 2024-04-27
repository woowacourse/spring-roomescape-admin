package roomescape.dao;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.domain.ReservationTime;
import roomescape.dto.CreateReservationTimeRequest;

import java.time.LocalTime;
import java.util.List;

@JdbcTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationTimeDaoTest {

    ReservationTimeDao reservationTimeDao;

    @Autowired
    public ReservationTimeDaoTest(JdbcTemplate jdbcTemplate) {
        this.reservationTimeDao = new ReservationTimeDao(jdbcTemplate);
    }

    @Test
    void 예약시간을_생성한다() {
        CreateReservationTimeRequest request = new CreateReservationTimeRequest(LocalTime.of(10, 0));
        ReservationTime reservationTime = new ReservationTime(request.startAt());
        reservationTimeDao.create(reservationTime);

        List<ReservationTime> reservationTimes = reservationTimeDao.findAll();
        Assertions.assertThat(reservationTimes).hasSize(1);
    }

    @Test
    void 예약시간_목록을_조회한다() {
        List<ReservationTime> reservationTimes = reservationTimeDao.findAll();

        Assertions.assertThat(reservationTimes).hasSize(0);
    }

    @Test
    void 예약시간을_삭제한다() {
        CreateReservationTimeRequest request = new CreateReservationTimeRequest(LocalTime.of(10, 0));
        ReservationTime reservationTime = new ReservationTime(request.startAt());
        reservationTimeDao.create(reservationTime);

        reservationTimeDao.delete(1);

        List<ReservationTime> reservationTimes = reservationTimeDao.findAll();
        Assertions.assertThat(reservationTimes).hasSize(0);
    }
}
