package roomescape.reservation.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.reservation.domain.Reservation;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class JdbcTemplateReservationDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private DataSource dataSource;

    private ReservationDao reservationDao;

    @BeforeEach
    void init() {
        reservationDao = new ReservationDao(jdbcTemplate, dataSource);
    }

    @DisplayName("예약 정보 삽입 테스트")
    @Test
    void insertTest() {
        Reservation insert = reservationDao.insert(new Reservation(null, "name", "2000-09-07", "10:00"));
        assertThat(insert.getId()).isEqualTo(1L);
    }

    @DisplayName("예약 정보 전체 조회 테스트")
    @Test
    void findAllTest() {
        reservationDao.insert(new Reservation(null, "name1", "2000-09-07", "10:00"));
        reservationDao.insert(new Reservation(null, "name2", "2000-09-07", "10:00"));
        reservationDao.insert(new Reservation(null, "name3", "2000-09-07", "10:00"));

        int findSize = reservationDao.findAll().size();
        assertThat(findSize).isEqualTo(3);
    }

    @DisplayName("예약 정보 삭제 테스트")
    @Test
    void deleteTest() {
        Reservation insert = reservationDao.insert(new Reservation(null, "name", "2000-09-07", "10:00"));
        int deleteCount = reservationDao.deleteById(insert.getId());
        int findSize = reservationDao.findAll().size();

        assertThat(deleteCount).isEqualTo(1);
        assertThat(findSize).isEqualTo(0);
    }
}
