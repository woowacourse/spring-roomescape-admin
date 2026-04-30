package roomescape.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.domain.ReservationTime;

import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
public class ReservationTimeDAOTest {
    private ReservationTimeDAO reservationTimeDAO;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setup() {
        reservationTimeDAO = new ReservationTimeDAO(jdbcTemplate);
        jdbcTemplate.execute("DROP TABLE reservation IF EXISTS");
        jdbcTemplate.execute("DROP TABLE reservation_time IF EXISTS");
        jdbcTemplate.execute("CREATE TABLE reservation_time(" +
                "id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, start_at VARCHAR(255))");

        jdbcTemplate.update("INSERT INTO reservation_time(start_at) VALUES (?)", "15:00");
        jdbcTemplate.update("INSERT INTO reservation_time(start_at) VALUES (?)", "16:00");
        jdbcTemplate.update("INSERT INTO reservation_time(start_at) VALUES (?)", "17:00");
    }

    @Test
    void count() {
        int count = reservationTimeDAO.count();

        assertThat(count).isEqualTo(3);
    }

    @Test
    void findReservationTimeById() {
        ReservationTime reservationTime = reservationTimeDAO.findReservationTimeById(1L);

        assertThat(reservationTime).isNotNull();
        assertThat(reservationTime.getStartAt()).isEqualTo("15:00");
    }

    @Test
    void findAllReservationTime() {
        List<ReservationTime> reservationTimes = reservationTimeDAO.findAllReservationTime();

        assertThat(reservationTimes).hasSize(3);
    }

    @Test
    void insert() {
        ReservationTime reservationTime = new ReservationTime(LocalTime.of(15, 0));
        reservationTimeDAO.insert(reservationTime);
    }

    @Test
    void keyHolder() {
        ReservationTime reservationTime = new ReservationTime(LocalTime.of(15, 0));
        Long id = reservationTimeDAO.insertWithKeyHolder(reservationTime);

        assertThat(id).isNotNull();
    }

    @Test
    void delete() {
        int rowNum = reservationTimeDAO.delete(1L);

        assertThat(rowNum).isEqualTo(1);
    }
}
