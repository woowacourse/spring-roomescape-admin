package roomescape.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeRequest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationTimeDaoTest {

    @Autowired
    private ReservationTimeDao reservationTimeDao;

    @Test
    void add() {
        // given
        LocalTime time = LocalTime.of(1, 2);
        ReservationTimeRequest reservationTimeRequest = new ReservationTimeRequest(time);

        // when
        reservationTimeDao.add(reservationTimeRequest);

        // then
        List<ReservationTime> reservationTimes = reservationTimeDao.findAll();
        assertThat(reservationTimes).containsExactly(new ReservationTime(1, time));
    }

    @Test
    void findAll() {
        // given
        LocalTime time = LocalTime.of(1, 2);
        ReservationTimeRequest reservationTimeRequest = new ReservationTimeRequest(time);

        // when
        reservationTimeDao.add(reservationTimeRequest);

        // then
        List<ReservationTime> reservationTimes = reservationTimeDao.findAll();
        assertThat(reservationTimes).containsExactly(new ReservationTime(1, time));
    }

    @Test
    void delete() {
        // given
        LocalTime time = LocalTime.of(1, 2);
        ReservationTimeRequest reservationTimeRequest = new ReservationTimeRequest(time);
        reservationTimeDao.add(reservationTimeRequest);

        // when
        reservationTimeDao.delete(1);

        // then
        List<ReservationTime> reservationTimes = reservationTimeDao.findAll();
        assertThat(reservationTimes).doesNotContain(new ReservationTime(1, time));
    }
}
