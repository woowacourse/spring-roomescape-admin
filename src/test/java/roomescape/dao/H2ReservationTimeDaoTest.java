package roomescape.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import roomescape.entity.ReservationTime;
import roomescape.service.Fixtures;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Sql(value = "/setReservationTime.sql")
class H2ReservationTimeDaoTest {

    @Autowired
    private ReservationTimeDao reservationTimeDao;

    @Test
    void findAll() {
        List<ReservationTime> reservationTimes = List.of(Fixtures.FIRST_TIME, Fixtures.SECOND_TIME);

        assertThat(reservationTimeDao.findAll()).isEqualTo(reservationTimes);
    }

    @Test
    void findById() {
        assertThat(reservationTimeDao.findById(1)).isEqualTo(Fixtures.FIRST_TIME);
    }

    @Test
    void save() {
        reservationTimeDao.save(Fixtures.THIRD_TIME);

        List<ReservationTime> expected = List.of(Fixtures.FIRST_TIME, Fixtures.SECOND_TIME, Fixtures.THIRD_TIME);

        assertThat(reservationTimeDao.findAll()).isEqualTo(expected);
    }

    @Test
    void deleteById() {
        reservationTimeDao.deleteById(1);

        assertThat(reservationTimeDao.findAll()).isEqualTo(List.of(Fixtures.SECOND_TIME));
    }

    @Test
    void deleteAll() {
        reservationTimeDao.deleteAll();

        assertThat(reservationTimeDao.findAll()).isEqualTo(List.of());
    }
}
