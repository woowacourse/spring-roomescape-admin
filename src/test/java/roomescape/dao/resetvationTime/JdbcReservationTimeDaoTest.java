package roomescape.dao.resetvationTime;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import roomescape.domain.ReservationTime;
import roomescape.dto.request.ReservationTimeCreateRequest;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class JdbcReservationTimeDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private JdbcReservationTimeDao jdbcReservationTimeDao;

    @BeforeEach
    void setUp() {
        jdbcTemplate.update("DELETE FROM reservation_time");
    }

    @DisplayName("예약을 데이터베이스에 추가한다.")
    @Test
    void addTest() {

        // given
        jdbcReservationTimeDao.create(new ReservationTimeCreateRequest(LocalTime.of(10, 10)));

        // when
        List<ReservationTime> reservationTimes = jdbcReservationTimeDao.findAll();

        // then
        assertThat(reservationTimes.getFirst().getStartAt()).isEqualTo(LocalTime.of(10, 10));
    }

    @DisplayName("데이터이스에 있는 예약 정보들을 가져온다.")
    @Test
    void findAllTest() {

        // given
        jdbcReservationTimeDao.create(new ReservationTimeCreateRequest(LocalTime.of(10, 10)));
        jdbcReservationTimeDao.create(new ReservationTimeCreateRequest(LocalTime.of(11, 10)));

        // when
        List<ReservationTime> reservationTimes = jdbcReservationTimeDao.findAll();

        // then
        assertThat(reservationTimes.size()).isEqualTo(2);
    }

    @DisplayName("데이터이스에 있는 예약 정보를 삭제한다.")
    @Test
    void deleteTest() {

        // given
        Long id = jdbcReservationTimeDao.create(new ReservationTimeCreateRequest(LocalTime.of(10, 10)));

        // when
        jdbcReservationTimeDao.delete(id);
        List<ReservationTime> reservationTimes = jdbcReservationTimeDao.findAll();

        // then
        assertThat(reservationTimes.size()).isEqualTo(0);
    }
}
