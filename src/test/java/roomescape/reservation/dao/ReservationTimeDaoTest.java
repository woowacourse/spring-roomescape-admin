package roomescape.reservation.dao;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.reservation.domain.ReservationTime;

import javax.sql.DataSource;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationTimeDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private DataSource dataSource;

    private ReservationTimeDao reservationTimeDao;

    @BeforeEach
    void init() {
        reservationTimeDao = new ReservationTimeDao(jdbcTemplate, dataSource);
    }

    @DisplayName("예약 시간 삽입 테스트")
    @Test
    void insertTest() {
        // given
        ReservationTime reservationTime = new ReservationTime("10:00");

        //when
        ReservationTime inserted = reservationTimeDao.insert(reservationTime);

        //then
        assertThat(inserted.getId()).isEqualTo(1L);
    }

    @DisplayName("예약 시간 전체 조회 테스트")
    @Test
    void findAllTest() {
        // given
        reservationTimeDao.insert(new ReservationTime("10:00"));
        reservationTimeDao.insert(new ReservationTime("10:00"));
        reservationTimeDao.insert(new ReservationTime("10:00"));

        //when
        List<ReservationTime> reservationTimes = reservationTimeDao.findAll();

        //then
        assertThat(reservationTimes.size()).isEqualTo(3);
    }

    @DisplayName("예약 시간 삭제 테스트")
    @Test
    void deleteTest() {
        // given
        ReservationTime reservationTime = new ReservationTime("10:00");
        ReservationTime inserted = reservationTimeDao.insert(reservationTime);

        //when & then
        Assertions.assertThatCode(() -> reservationTimeDao.deleteById(inserted.getId()));
    }
}
