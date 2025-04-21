package roomescape.dao;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import roomescape.domain.Reservation;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

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

        jdbcTemplate.execute(
                "CREATE TABLE reservation (" +
                        "id BIGINT NOT NULL AUTO_INCREMENT, " +
                        "name VARCHAR(255) NOT NULL, " +
                        "date VARCHAR(255) NOT NULL, " +
                        "time VARCHAR(255) NOT NULL, " +
                        "PRIMARY KEY (id)" +
                        ")"
        );

        reservationDao = new ReservationDao(jdbcTemplate);
    }

    @BeforeEach
    void clear() {
        reservationDao.deleteAll();
    }

    @Test
    void 데이터베이스에_예약_기록을_추가할_수_있다() {
        // when & then
        assertThat(reservationDao.insert(new Reservation("메이", LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0, 0)))))
                .isEqualTo(1);
    }

    @Test
    void 데이터베이스에서_예약_목록을_가져올_수_있다() {
        // given
        reservationDao.insert(new Reservation("메이", LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0, 0))));
        reservationDao.insert(new Reservation("may", LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0, 0))));

        // when & then
        assertThat(reservationDao.findAllReservations().size())
                .isEqualTo(2);
    }

    @Test
    void 데이터베이스의_예약_목록을_삭제할_수_있다() {
        // given
        reservationDao.insert(new Reservation("메이", LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0, 0))));
        reservationDao.insert(new Reservation("may", LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0, 0))));

        Long id = reservationDao.findAllReservations().get(0).getId();

        // when & then
        assertThat(reservationDao.delete(id))
                .isEqualTo(1);
    }
}
