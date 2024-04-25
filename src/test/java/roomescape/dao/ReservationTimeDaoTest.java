package roomescape.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.domain.ReservationTime;
import roomescape.dto.TimeRequest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationTimeDaoTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private ReservationTimeDao reservationTimeDao;

    @DisplayName("reservation 조회 테스트")
    @Test
    void findAll() {
        jdbcTemplate.update("INSERT INTO reservation_time (id, start_at) VALUES (?, ?)", 1, "10:00");

        List<ReservationTime> reservationTimes = reservationTimeDao.findAll();
        Integer count = jdbcTemplate.queryForObject("SELECT count(1) FROM reservation_time", Integer.class);
        assertThat(reservationTimes.size()).isEqualTo(count);
    }

    @DisplayName("reservation 추가 테스트")
    @Test
    void add() {
        TimeRequest timeRequest = new TimeRequest(LocalTime.parse("10:00"));
        reservationTimeDao.add(timeRequest);

        Integer count = jdbcTemplate.queryForObject("SELECT count(1) FROM reservation_time", Integer.class);
        assertThat(count).isEqualTo(1);
    }

    @DisplayName("reservation 삭제 테스트")
    @Test
    void delete() {
        jdbcTemplate.update("INSERT INTO reservation_time (id, start_at) VALUES (?, ?)", 1, "10:00");
        reservationTimeDao.delete(1L);

        Integer countAfterDelete = jdbcTemplate.queryForObject("SELECT count(1) from reservation_time", Integer.class);
        assertThat(countAfterDelete).isEqualTo(0);
    }
}
