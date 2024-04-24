package roomescape.dao;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.controller.dto.CreateReservationTimeRequest;
import roomescape.domain.ReservationTime;

import java.time.LocalTime;
import java.util.List;

@JdbcTest
class ReservationTimeDaoTest {

    ReservationTimeDao reservationTimeDao;

    @Autowired
    public ReservationTimeDaoTest(JdbcTemplate jdbcTemplate) {
        this.reservationTimeDao = new ReservationTimeDao(jdbcTemplate);
    }

    @Test
    void 예약시간을_생성한다() {
        CreateReservationTimeRequest createReservationTimeRequest = new CreateReservationTimeRequest(LocalTime.of(10, 0));

        reservationTimeDao.create(createReservationTimeRequest);

        List<ReservationTime> reservationTimes = reservationTimeDao.readAll();
        Assertions.assertThat(reservationTimes).hasSize(1);
    }

    @Test
    void 예약시간_목록을_조회한다() {
        List<ReservationTime> reservationTimes = reservationTimeDao.readAll();

        Assertions.assertThat(reservationTimes).hasSize(0);
    }

    @Test
    void 예약시간을_삭제한다() {
        CreateReservationTimeRequest createReservationTimeRequest = new CreateReservationTimeRequest(LocalTime.of(10, 0));
        reservationTimeDao.create(createReservationTimeRequest);

        reservationTimeDao.delete(2);

        List<ReservationTime> reservationTimes = reservationTimeDao.readAll();
        Assertions.assertThat(reservationTimes).hasSize(0);
    }
}
