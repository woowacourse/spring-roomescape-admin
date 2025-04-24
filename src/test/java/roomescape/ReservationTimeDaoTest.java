package roomescape;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class ReservationTimeDaoTest {

    @Autowired
    private ReservationTimeDao reservationTimeDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        String createTableSql = """
                DROP TABLE IF EXISTS RESERVATION_TIME;
                CREATE TABLE RESERVATION_TIME
                (
                    id   BIGINT       NOT NULL AUTO_INCREMENT,
                    start_at VARCHAR(255) NOT NULL,
                    PRIMARY KEY (id)
                );
                """;
        jdbcTemplate.execute(createTableSql);
        String insertSql = """
                INSERT INTO RESERVATION_TIME(start_at) VALUES
                    ('13:40'),
                    ('14:40')
                """;
        jdbcTemplate.update(insertSql);
    }

    @Test
    @DisplayName("예약 시간을 저장한다")
    void saveTest() {
        // given
        ReservationTime time = new ReservationTime(null, LocalTime.of(10, 0));

        // when
        ReservationTime save = reservationTimeDao.save(time);

        // then
        ReservationTime saved = jdbcTemplate.queryForObject("SELECT * FROM RESERVATION_TIME WHERE id = ?",
                (rs, rowNum) -> new ReservationTime(
                        rs.getLong("id"),
                        rs.getTime("start_at").toLocalTime()
                ), save.getId());
        assertThat(saved).isNotNull();
        assertThat(saved.getStartAt()).isEqualTo(LocalTime.of(10, 0));
    }

    @Test
    @DisplayName("Id로 예약 시간을 조회한다")
    void getTimeTest() {
        // given
        Long id = 1L;

        // when
        Optional<ReservationTime> found = reservationTimeDao.findById(id);

        // then
        assertThat(found).isPresent();
        assertThat(found.get().getStartAt()).isEqualTo(LocalTime.of(13, 40));
    }

    @Test
    @DisplayName("존재하지 않는 Id의 예약 시간을 조회한다")
    void getNonExistTimeTest() {
        // given
        Long id = 3L;

        // when
        Optional<ReservationTime> found = reservationTimeDao.findById(id);

        // then
        assertThat(found).isEmpty();
    }

    @Test
    @DisplayName("모든 예약 시간을 조회한다")
    void getTimesTest() {
        // when
        List<ReservationTime> times = reservationTimeDao.findAll();

        // then
        assertThat(times).hasSize(2);
    }

    @Test
    @DisplayName("예약 시간을 삭제한다")
    void deleteTimeTest() {
        // given
        Long id = 1L;

        // when
        int deletedCount = reservationTimeDao.delete(1L);

        // then
        List<ReservationTime> times = jdbcTemplate.query("SELECT * FROM RESERVATION_TIME",
                (rs, rowNum) -> new ReservationTime(
                        rs.getLong("id"),
                        rs.getTime("start_at").toLocalTime()
                ));
        assertThat(deletedCount).isEqualTo(1);
        assertThat(times).hasSize(1);
    }
}
