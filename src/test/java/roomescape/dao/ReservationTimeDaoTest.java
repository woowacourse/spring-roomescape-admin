package roomescape.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.domain.ReservationTime;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationTimeDaoTest {

    @Autowired
    private ReservationTimeDao reservationTimeDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void getConnection() {
        try (Connection connection = jdbcTemplate.getDataSource().getConnection()) {
            assertThat(connection).isNotNull();
            assertThat(connection.getCatalog()).isEqualTo("DATABASE");
            assertThat(connection.getMetaData().getTables(null, null, "RESERVATION_TIME", null).next()).isTrue();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void add() {
        // given
        LocalTime time = LocalTime.of(1, 2);
        ReservationTime reservationTime = new ReservationTime(1L, time);

        // when
        reservationTimeDao.add(reservationTime);

        // then
        List<ReservationTime> reservationTimes = reservationTimeDao.findAll();
        assertThat(reservationTimes).containsExactly(new ReservationTime(1L, time));
    }

    @Test
    void findAll() {
        // given
        jdbcTemplate.update("INSERT INTO reservation_time (start_at) VALUES (?)", "10:00");

        // when
        List<ReservationTime> reservationTimes = reservationTimeDao.findAll();

        // then
        assertThat(reservationTimes).containsExactly(new ReservationTime(1L, LocalTime.of(10, 0)));
    }

    @Test
    void delete() {
        // given
        jdbcTemplate.update("INSERT INTO reservation_time (start_at) VALUES (?)", "10:00");


        // when
        reservationTimeDao.delete(1);

        // then
        List<ReservationTime> reservationTimes = reservationTimeDao.findAll();
        assertThat(reservationTimes).doesNotContain(new ReservationTime(1L, LocalTime.of(10, 0)));
    }
}
