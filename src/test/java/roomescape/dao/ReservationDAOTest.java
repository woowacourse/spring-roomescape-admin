package roomescape.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
public class ReservationDAOTest {
    private ReservationDAO reservationDAO;
    private List<ReservationTime> reservationTimes;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        reservationDAO = new ReservationDAO(jdbcTemplate);
        ReservationTimeDAO reservationTimeDAO = new ReservationTimeDAO(jdbcTemplate);

        jdbcTemplate.update("INSERT INTO reservation_time(start_at) VALUES (?)", "15:00");
        jdbcTemplate.update("INSERT INTO reservation_time(start_at) VALUES (?)", "16:00");
        jdbcTemplate.update("INSERT INTO reservation_time(start_at) VALUES (?)", "17:00");

        reservationTimes = reservationTimeDAO.findAllReservationTime();

        jdbcTemplate.update("INSERT INTO reservation(name, date, time_id) VALUES (?, ?, ?)",
                "user1", "2026-04-28", reservationTimes.getFirst().getId());
        jdbcTemplate.update("INSERT INTO reservation(name, date, time_id) VALUES (?, ?, ?)",
                "user2", "2026-04-29", reservationTimes.get(1).getId());
        jdbcTemplate.update("INSERT INTO reservation(name, date, time_id) VALUES (?, ?, ?)",
                "user3", "2026-04-30", reservationTimes.get(2).getId());

    }

    @Test
    void count() {
        int count = reservationDAO.count();

        assertThat(count).isEqualTo(3);
    }

    @Test
    void findReservationById() {
        List<Reservation> reservations = reservationDAO.findAllReservation();
        Long id = reservations.getFirst().getId();
        Reservation reservation = reservationDAO.findReservationById(id);

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
        ReservationTime time = reservationTimes.getFirst();
        Reservation reservation = new Reservation("user1", LocalDate.of(2026, 4, 29), time);
        reservationDAO.insert(reservation);
    }

    @Test
    void keyHolder() {
        ReservationTime time = reservationTimes.getFirst();
        Reservation reservation = new Reservation("user4", LocalDate.of(2026, 5, 1), time);
        Long id = reservationDAO.insertWithKeyHolder(reservation);

        assertThat(id).isNotNull();
    }

    @Test
    void delete() {
        List<Reservation> reservations = reservationDAO.findAllReservation();
        Long id = reservations.getFirst().getId();
        int rowNum = reservationDAO.delete(id);

        assertThat(rowNum).isEqualTo(1);
    }
}
