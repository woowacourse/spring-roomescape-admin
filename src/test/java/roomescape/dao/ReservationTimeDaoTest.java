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

    private final ReservationTime reservationTime = new ReservationTime(
            null,
            LocalTime.of(10,0)
    );

    @Test
    void save() {
        ReservationTime savedReservationTime = reservationTimeDao.save(reservationTime);

        assertThat(savedReservationTime.getId()).isEqualTo(1);
    }

    @Test
    void findAll() {
        reservationTimeDao.save(reservationTime);
        List<ReservationTime> reservationTimes = reservationTimeDao.findAll();

        assertThat(reservationTimes).size().isEqualTo(1);
    }

    @Test
    void delete() {
        ReservationTime savedReservationTime = reservationTimeDao.save(reservationTime);
        reservationTimeDao.delete(savedReservationTime.getId());

        assertThat(reservationTimeDao.findAll()).isEmpty();
    }
}
