package roomescape.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.domain.ReservationTime;

@JdbcTest
public class ReservationTimeDaoTest {

    private ReservationTimeDao reservationTimeDao;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        jdbcTemplate.execute("ALTER TABLE reservation_time ALTER COLUMN id RESTART WITH 1");
        reservationTimeDao = new ReservationTimeDao(jdbcTemplate);
        jdbcTemplate.update("insert into reservation_time (start_at) values (?)",
                "10:00"
        );

        jdbcTemplate.update("insert into reservation_time (start_at) values (?)",
                "11:00"
        );

        jdbcTemplate.update("insert into reservation_time (start_at) values (?)",
                "12:00"
        );
    }

    @DisplayName("시간을 저장하는지 확인합니다.")
    @Test
    void insertTest() {
        ReservationTime reservationTime = new ReservationTime(1, LocalTime.of(13, 0));
        reservationTimeDao.insert(reservationTime);
        int size = jdbcTemplate.queryForObject("select count(*) from reservation_time", Integer.class);

        assertThat(size).isEqualTo(4);
    }

    @DisplayName("모든 시간을 조회하는지 확인합니다.")
    @Test
    void findAllTest() {
        List<ReservationTime> reservationTimes = reservationTimeDao.findAll();

        assertThat(reservationTimes.size()).isEqualTo(3);
    }

    @DisplayName("id로 시간을 조회하는지 확인합니다.")
    @Test
    void findByIdTest() {
        ReservationTime reservationTime = reservationTimeDao.findById(1);
        ReservationTime answer = new ReservationTime(1, LocalTime.of(10, 0));
        assertThat(reservationTime).isEqualTo(answer);
    }

    @DisplayName("id로 시간을 삭제하는지 확인합니다.")
    @Test
    public void deleteByIdTest() {
        int effectedRowsCount = reservationTimeDao.deleteById(2);

        List<ReservationTime> reservationTimes = reservationTimeDao.findAll();
        assertAll(
                () -> assertThat(reservationTimes.size()).isEqualTo(2),
                () -> assertThat(effectedRowsCount).isEqualTo(1)
        );
    }

    @DisplayName("삭제하려는 id가 없는 경우 예외가 발생합니다.")
    @Test
    void deleteByIdErrorTest() {
        assertThatCode(() -> reservationTimeDao.deleteById(10))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("id가 존재하지 않습니다.");
    }
}
