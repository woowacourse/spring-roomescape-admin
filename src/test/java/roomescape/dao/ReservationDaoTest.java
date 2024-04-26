package roomescape.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationDaoTest {

    @Autowired
    private ReservationDao reservationDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void getConnection() {
        try (Connection connection = jdbcTemplate.getDataSource().getConnection()) {
            assertThat(connection).isNotNull();
            assertThat(connection.getCatalog()).isEqualTo("DATABASE");
            assertThat(connection.getMetaData().getTables(null, null, "RESERVATION", null).next()).isTrue();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void findAll() {
        // given
        jdbcTemplate.update("INSERT INTO reservation_time (id, start_at) VALUES (?, ?)", 1, "10:00");
        jdbcTemplate.update("INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)", "커비", "2023-01-01", 1);
        ReservationTime reservationTime = new ReservationTime(1L, LocalTime.of(10, 0));

        // when
        List<Reservation> reservations = reservationDao.findAll();

        // then
        assertThat(reservations).containsExactly(new Reservation(1L, "커비", LocalDate.of(2023, 1, 1), reservationTime));
    }

    @Test
    void add() {
        // given
        jdbcTemplate.update("INSERT INTO reservation_time (start_at) VALUES (?)", "10:00");
        ReservationTime reservationTime = new ReservationTime(1L, LocalTime.of(10, 0));
        Reservation reservation = new Reservation("커비", LocalDate.of(2023, 1, 1), reservationTime);

        // when
        reservationDao.add(reservation);

        // then
        List<Reservation> reservations = reservationDao.findAll();
        assertThat(reservations).containsExactly(new Reservation(1L, "커비", LocalDate.of(2023, 1, 1), reservationTime));
    }

    @Test
    void delete() {
        // given
        jdbcTemplate.update("INSERT INTO reservation_time (start_at) VALUES (?)", "10:00");
        jdbcTemplate.update("INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)", "커비", "2023-01-01", 1);
        ReservationTime reservationTime = new ReservationTime(1L, LocalTime.of(10, 0));

        // when
        reservationDao.delete(1);

        // then
        List<Reservation> reservations = reservationDao.findAll();
        assertThat(reservations).doesNotContain(new Reservation(1L, "커비", LocalDate.of(2023, 1, 1), reservationTime));
    }
}
