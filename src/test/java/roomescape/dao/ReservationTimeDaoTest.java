package roomescape.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.domain.ReservationTime;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationTimeDaoTest {

    @Autowired
    private ReservationTimeDao reservationTimeDao;

    private final ReservationTime time = new ReservationTime(
            null,
            LocalTime.of(10,0)
    );

    @Test
    void save() {
        ReservationTime savedTime = reservationTimeDao.save(time);

        assertThat(savedTime.getId()).isEqualTo(1);
    }

    @Test
    void findAll() {
        reservationTimeDao.save(time);
        reservationTimeDao.save(time);
        List<ReservationTime> reservationTimes = reservationTimeDao.findAll();

        assertThat(reservationTimes).size().isEqualTo(2);
    }

    @Test
    void delete() {
        ReservationTime savedTime = reservationTimeDao.save(time);
        reservationTimeDao.delete(savedTime.getId());

        assertThat(reservationTimeDao.findAll()).isEmpty();
    }
}
