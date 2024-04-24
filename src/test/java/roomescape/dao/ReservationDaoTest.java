package roomescape.dao;

import static org.assertj.core.api.Assertions.assertThat;

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
    void findAll() {
        // given
        jdbcTemplate.update("INSERT INTO reservation_time (start_at) VALUES (?)", "10:00");
        String name = "커비";
        LocalDate date = LocalDate.of(2023, 1, 1);
        LocalTime startAt = LocalTime.of(10, 0);
        ReservationTime reservationTime = new ReservationTime(1L, startAt);
        Reservation reservation = new Reservation(name, date, reservationTime);

        reservationDao.add(reservation);

        // when
        List<Reservation> reservations = reservationDao.findAll();

        // then
        assertThat(reservations).containsExactly(new Reservation(1L, name, date, reservationTime));
    }

    @Test
    void add() {
        // given
        jdbcTemplate.update("INSERT INTO reservation_time (start_at) VALUES (?)", "10:00");
        String name = "커비";
        LocalDate date = LocalDate.of(2023, 1, 1);
        LocalTime startAt = LocalTime.of(10, 0);
        ReservationTime reservationTime = new ReservationTime(1L, startAt);
        Reservation reservation = new Reservation(name, date, reservationTime);

        // when
        reservationDao.add(reservation);

        // then
        List<Reservation> reservations = reservationDao.findAll();
        assertThat(reservations).containsExactly(new Reservation(1L, name, date, reservationTime));
    }

    @Test
    void delete() {
        // given
        jdbcTemplate.update("INSERT INTO reservation_time (start_at) VALUES (?)", "10:00");
        String name = "커비";
        LocalDate date = LocalDate.of(2023, 1, 1);
        LocalTime startAt = LocalTime.of(10, 0);
        ReservationTime reservationTime = new ReservationTime(1L, startAt);
        Reservation reservation = new Reservation(name, date, reservationTime);

        reservationDao.add(reservation);

        // when
        reservationDao.delete(1);

        // then
        List<Reservation> reservations = reservationDao.findAll();
        assertThat(reservations).doesNotContain(new Reservation(1L, name, date, reservationTime));
    }
}
