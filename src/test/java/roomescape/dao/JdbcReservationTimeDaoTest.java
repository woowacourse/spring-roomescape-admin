package roomescape.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import roomescape.domain.ReservationTime;
import roomescape.service.Fixtures;
import roomescape.domain.ReservationTimeDao;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Sql(value = "/setReservationTime.sql")
class JdbcReservationTimeDaoTest {

    @Autowired
    private ReservationTimeDao reservationTimeDao;

    @DisplayName("DB에 저장된 전체 예약 시간을 반환한다.")
    @Test
    void findAll() {
        List<ReservationTime> reservationTimes = List.of(Fixtures.FIRST_TIME, Fixtures.SECOND_TIME);

        assertThat(reservationTimeDao.findAll()).isEqualTo(reservationTimes);
    }

    @DisplayName("해당 id의 예약 시간을 반환한다.")
    @Test
    void findById() {
        assertThat(reservationTimeDao.findById(1)).isEqualTo(Fixtures.FIRST_TIME);
    }

    @DisplayName("예약 시간을 DB에 저장한다.")
    @Test
    void save() {
        reservationTimeDao.save(Fixtures.THIRD_TIME);

        List<ReservationTime> expected = List.of(Fixtures.FIRST_TIME, Fixtures.SECOND_TIME, Fixtures.THIRD_TIME);

        assertThat(reservationTimeDao.findAll()).isEqualTo(expected);
    }

    @DisplayName("해당 id의 예약 시간을 삭제한다.")
    @Test
    void deleteById() {
        reservationTimeDao.deleteById(1);

        assertThat(reservationTimeDao.findAll()).isEqualTo(List.of(Fixtures.SECOND_TIME));
    }

    @DisplayName("DB의 전체 예약 시간을 삭제한다.")
    @Test
    void deleteAll() {
        reservationTimeDao.deleteAll();

        assertThat(reservationTimeDao.findAll()).isEqualTo(List.of());
    }
}
