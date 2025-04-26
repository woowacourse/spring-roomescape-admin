package roomescape.dao;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import roomescape.dto.create.ReservationCreate;

import javax.sql.DataSource;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class ReservationDaoTest {

    private static ReservationDao reservationDao;

    @BeforeAll
    static void setUp() {
        DataSource dataSource = new DriverManagerDataSource(
                "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1",
                "sa",
                ""
        );

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcTemplate.execute("DROP TABLE IF EXISTS reservation");
        jdbcTemplate.execute("DROP TABLE IF EXISTS reservation_time");

        jdbcTemplate.execute(
                "CREATE TABLE reservation_time (" +
                        "id BIGINT NOT NULL AUTO_INCREMENT, " +
                        "start_at VARCHAR(255) NOT NULL, " +
                        "PRIMARY KEY (id)" +
                        ")"
        );

        jdbcTemplate.execute(
                "CREATE TABLE reservation (" +
                        "id BIGINT NOT NULL AUTO_INCREMENT, " +
                        "name VARCHAR(255) NOT NULL, " +
                        "date VARCHAR(255) NOT NULL, " +
                        "time_id BIGINT, " +
                        "PRIMARY KEY (id), " +
                        "FOREIGN KEY (time_id) REFERENCES reservation_time (id)" +
                        ")"
        );

        jdbcTemplate.execute("INSERT INTO reservation_time(start_at) VALUES ('11:10')");

        reservationDao = new ReservationDao(jdbcTemplate);
    }

    @BeforeEach
    void clear() {
        reservationDao.deleteAll();
    }

    @Test
    void 데이터베이스에_예약_기록을_추가할_수_있다() {
        // when & then
        assertThat(reservationDao.save(new ReservationCreate("메이", getTodayDate(), 1L)))
                .isEqualTo(1);
    }

    @Test
    void 데이터베이스에서_예약_목록을_가져올_수_있다() {
        // given
        reservationDao.save(new ReservationCreate("메이", getTodayDate(), 1L));
        reservationDao.save(new ReservationCreate("may", getTomorrowDate(), 1L));

        // when & then
        assertThat(reservationDao.getAll().size())
                .isEqualTo(2);
    }

    @Test
    void 데이터베이스의_예약_목록을_삭제할_수_있다() {
        // given
        reservationDao.save(new ReservationCreate("메이", getTodayDate(), 1L));
        reservationDao.save(new ReservationCreate("메이", getTomorrowDate(), 1L));

        Long id = reservationDao.getAll().get(0).reservationId();

        // when & then
        assertThat(reservationDao.delete(id))
                .isEqualTo(1);
    }

    private String getTodayDate() {
        return LocalDate.now().toString();
    }

    private String getTomorrowDate() {
        return LocalDate.now().plusDays(1).toString();
    }
}
