package roomescape.dao;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import roomescape.domain.ReservationTime;

import javax.sql.DataSource;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

public class ReservationTimeDaoTest {

    private static ReservationTimeDao reservationTimeDao;

    @BeforeAll
    static void setUp() {
        DataSource dataSource = new DriverManagerDataSource(
                "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1",
                "sa",
                ""
        );

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcTemplate.execute(
                "CREATE TABLE reservation_time (" +
                        "id BIGINT NOT NULL AUTO_INCREMENT, " +
                        "start_at VARCHAR(255) NOT NULL, " +
                        "PRIMARY KEY (id)" +
                        ")"
        );

        reservationTimeDao = new ReservationTimeDao(jdbcTemplate);
    }

    @BeforeEach
    void clear() {
        reservationTimeDao.deleteAll();
    }

    @Test
    void 데이터베이스에_예약_시간을_추가할_수_있다() {
        // when & then
        assertThat(reservationTimeDao.save(new ReservationTime(LocalTime.of(10, 0))))
                .isEqualTo(1);
    }

    @Test
    void 데이터베이스에서_예약_시간_목록을_가져올_수_있다() {
        // given
        reservationTimeDao.save(new ReservationTime(LocalTime.of(9, 0, 0)));
        reservationTimeDao.save(new ReservationTime(LocalTime.of(10, 0, 0)));

        // when & then
        assertThat(reservationTimeDao.getAll().size())
                .isEqualTo(2);
    }

    @Test
    void 데이터베이스의_예약_시간을_삭제할_수_있다() {
        // given
        reservationTimeDao.save(new ReservationTime(LocalTime.of(9, 0, 0)));
        reservationTimeDao.save(new ReservationTime(LocalTime.of(10, 0, 0)));

        Long id = reservationTimeDao.getAll().get(0).getId();

        // when & then
        assertThat(reservationTimeDao.delete(id))
                .isEqualTo(1);
    }
}
