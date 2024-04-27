package roomescape.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import roomescape.entity.Reservation;
import roomescape.service.Fixtures;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Sql(value = "/setReservation.sql")
class H2ReservationDaoTest {

    @Autowired
    private ReservationDao reservationDao;

    @Test
    void findAll() {
        List<Reservation> reservations = List.of(Fixtures.FIRST_RESERVATION, Fixtures.SECOND_RESERVATION);

        assertThat(reservationDao.findAll()).isEqualTo(reservations);
    }

    @Test
    void findById() {
        assertThat(reservationDao.findById(1)).isEqualTo(Fixtures.FIRST_RESERVATION);
    }

    @Test
    void save() {
        reservationDao.save(Fixtures.THIRD_RESERVATION);

        List<Reservation> expected = List.of(Fixtures.FIRST_RESERVATION, Fixtures.SECOND_RESERVATION, Fixtures.THIRD_RESERVATION);

        assertThat(reservationDao.findAll()).isEqualTo(expected);
    }

    @Test
    void deleteById() {
        reservationDao.deleteById(1);

        assertThat(reservationDao.findAll()).isEqualTo(List.of(Fixtures.SECOND_RESERVATION));
    }

    @Test
    void deleteAll() {
        reservationDao.deleteAll();

        assertThat(reservationDao.findAll()).isEqualTo(List.of());
    }
}
