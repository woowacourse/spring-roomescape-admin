package roomescape.reservation.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.domain.ReservationTime;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private DataSource dataSource;

    private ReservationDao reservationDao;

    private ReservationTime reservationTime;

    @BeforeEach
    void init() {
        reservationDao = new ReservationDao(jdbcTemplate, dataSource);

        ReservationTimeDao reservationTimeDao = new ReservationTimeDao(jdbcTemplate, dataSource);
        reservationTime = reservationTimeDao.insert(new ReservationTime("10:00"));
    }

    @DisplayName("예약 정보 삽입 테스트")
    @Test
    void insertTest() {
        // given
        Reservation reservation = new Reservation("name", "2000-09-07", reservationTime);

        // when
        Reservation insert = reservationDao.insert(reservation);

        //then
        assertThat(insert.getId()).isEqualTo(1L);
    }

    @DisplayName("예약 정보 전체 조회 테스트")
    @Test
    void findAllTest() {
        // given
        reservationDao.insert(new Reservation("name1", "2000-09-07", reservationTime));
        reservationDao.insert(new Reservation("name2", "2000-09-07", reservationTime));
        reservationDao.insert(new Reservation("name3", "2000-09-07", reservationTime));

        // when
        int findSize = reservationDao.findAll().size();

        // then
        assertThat(findSize).isEqualTo(3);
    }

    @DisplayName("예약 정보 삭제 테스트")
    @Test
    void deleteTest() {
        //given
        Reservation insert = reservationDao.insert(new Reservation("name", "2000-09-07", reservationTime));

        // when
        int deleteCount = reservationDao.deleteById(insert.getId());
        int findSize = reservationDao.findAll().size();

        // then
        Assertions.assertAll(
                () -> assertThat(deleteCount).isEqualTo(1),
                () -> assertThat(findSize).isEqualTo(0)
        );
    }
}
