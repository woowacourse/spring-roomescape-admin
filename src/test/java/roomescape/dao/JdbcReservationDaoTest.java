package roomescape.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationDao;
import roomescape.service.Fixtures;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Sql(value = "/setReservation.sql")
class JdbcReservationDaoTest {

    @Autowired
    private ReservationDao reservationDao;

    @DisplayName("DB에 저장된 전체 예약을 반환한다.")
    @Test
    void findAll() {
        List<Reservation> reservations = List.of(Fixtures.FIRST_RESERVATION, Fixtures.SECOND_RESERVATION);

        assertThat(reservationDao.findAll()).isEqualTo(reservations);
    }

    @DisplayName("해당 id의 예약을 반환한다.")
    @Test
    void findById() {
        assertThat(reservationDao.findById(1)).isEqualTo(Fixtures.FIRST_RESERVATION);
    }

    @DisplayName("예약을 DB에 저장한다.")
    @Test
    void save() {
        reservationDao.save(Fixtures.THIRD_RESERVATION);

        List<Reservation> expected = List.of(Fixtures.FIRST_RESERVATION, Fixtures.SECOND_RESERVATION, Fixtures.THIRD_RESERVATION);

        assertThat(reservationDao.findAll()).isEqualTo(expected);
    }

    @DisplayName("해당 id의 예약을 삭제한다.")
    @Test
    void deleteById() {
        reservationDao.deleteById(1);

        assertThat(reservationDao.findAll()).isEqualTo(List.of(Fixtures.SECOND_RESERVATION));
    }

    @DisplayName("DB의 전체 예약을 삭제한다.")
    @Test
    void deleteAll() {
        reservationDao.deleteAll();

        assertThat(reservationDao.findAll()).isEqualTo(List.of());
    }
}
