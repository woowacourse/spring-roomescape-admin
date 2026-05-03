package roomescape.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.repository.ReservationQueryingRepository;
import roomescape.domain.Reservation;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Import(ReservationQueryingRepository.class)
public class ReservationQueryingRepositoryTest {

    @Autowired
    private ReservationQueryingRepository reservationQueryingRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        jdbcTemplate.execute("DROP TABLE IF EXISTS reservation");
        jdbcTemplate.execute("DROP TABLE IF EXISTS reservation_time");

        jdbcTemplate.execute("""
                CREATE TABLE reservation_time (
                    id       BIGINT       NOT NULL AUTO_INCREMENT,
                    start_at VARCHAR(255) NOT NULL,
                    PRIMARY KEY (id)
                )""");

        jdbcTemplate.execute("""
                CREATE TABLE reservation (
                    id      BIGINT       NOT NULL AUTO_INCREMENT,
                    name    VARCHAR(255) NOT NULL,
                    date    VARCHAR(255) NOT NULL,
                    time_id BIGINT,
                    PRIMARY KEY (id),
                    FOREIGN KEY (time_id) REFERENCES reservation_time (id)
                )""");

        jdbcTemplate.execute("INSERT INTO reservation_time (start_at) VALUES ('10:00:00')");
        jdbcTemplate.execute("INSERT INTO reservation_time (start_at) VALUES ('11:24:00')");
        jdbcTemplate.execute("INSERT INTO reservation_time (start_at) VALUES ('21:38:00')");

        jdbcTemplate.update("INSERT INTO reservation (name, date, time_id) VALUES ('현미밥', '2026-04-29', 1)");
        jdbcTemplate.update("INSERT INTO reservation (name, date, time_id) VALUES ('테리', '2026-04-30', 2)");
        jdbcTemplate.update("INSERT INTO reservation (name, date, time_id) VALUES ('주니', '2026-05-05', 3)");
    }

    @Test
    @DisplayName("전체 예약 목록을 조회한다")
    void findAll() {
        List<Reservation> reservations = reservationQueryingRepository.findAll();

        assertThat(reservations).hasSize(3);
        assertThat(reservations.get(0).getName()).isEqualTo("현미밥");
        assertThat(reservations.get(1).getName()).isEqualTo("테리");
        assertThat(reservations.get(2).getName()).isEqualTo("주니");
    }
}
