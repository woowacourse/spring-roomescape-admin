package roomescape;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
public class ReservationDAOTest {
    private ReservationDAO reservationDAO;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        reservationDAO = new ReservationDAO(jdbcTemplate);

        jdbcTemplate.execute("DROP TABLE reservation IF EXISTS");
        jdbcTemplate.execute("CREATE TABLE reservation(" +
                "id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, name VARCHAR(255), date DATE, time TIME)");

        jdbcTemplate.update("INSERT INTO reservation(name, date, time) VALUES (?, ?, ?)", "user1", "2026-04-28", "15:00");
        jdbcTemplate.update("INSERT INTO reservation(name, date, time) VALUES (?, ?, ?)", "user2", "2026-04-29", "16:00");
        jdbcTemplate.update("INSERT INTO reservation(name, date, time) VALUES (?, ?, ?)", "user3", "2026-04-30", "17:00");

    }

    @Test
    void count() {
        int count = reservationDAO.count();

        assertThat(count).isEqualTo(3);
    }

    @Test
    void findReservationById() {
        Reservation reservation = reservationDAO.findReservationById(1L);

        assertThat(reservation).isNotNull();
        assertThat(reservation.getName()).isEqualTo("user1");
    }

    @Test
    void findAllReservation() {
        List<Reservation> reservations = reservationDAO.findAllReservation();

        assertThat(reservations).hasSize(3);
    }

    @Test
    void insert() {
        Reservation reservation = new Reservation("user1", LocalDate.of(2026, 4, 29), LocalTime.of(14, 0, 0));
        reservationDAO.insert(reservation);
    }

    @Test
    void keyHolder() {
        Reservation reservation = new Reservation("user4", LocalDate.of(2026, 5, 1), LocalTime.of(15, 0, 0));
        Long id = reservationDAO.insertWithKeyHolder(reservation);

        assertThat(id).isNotNull();
    }
}
