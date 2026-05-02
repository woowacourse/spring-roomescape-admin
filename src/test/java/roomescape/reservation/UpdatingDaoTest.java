package roomescape.reservation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.repository.ReservationUpdatingRepository;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Import(ReservationUpdatingRepository.class)
public class UpdatingDaoTest {

    @Autowired
    private ReservationUpdatingRepository reservationUpdatingRepository;

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

        jdbcTemplate.execute("INSERT INTO reservation_time (start_at) VALUES ('15:30:00')");
    }

    @Test
    @DisplayName("새로운 예약을 추가하면 생성된 ID를 반환한다")
    void insert() {
        Long timeId = jdbcTemplate.queryForObject("SELECT id FROM reservation_time LIMIT 1", Long.class);
        ReservationTime time = new ReservationTime(timeId, LocalTime.parse("15:30:00"));
        Reservation reservation = new Reservation(null, "가현", LocalDate.parse("2026-05-01"), time);

        Long generatedId = reservationUpdatingRepository.insert(reservation);

        assertThat(generatedId).isNotNull();
        Integer count = jdbcTemplate.queryForObject("SELECT count(*) FROM reservation WHERE id = ?", Integer.class, generatedId);
        assertThat(count).isEqualTo(1);
    }

    @Test
    @DisplayName("예약을 삭제하면 해당 데이터가 DB에서 제거된다")
    void delete() {
        Long timeId = jdbcTemplate.queryForObject("SELECT id FROM reservation_time LIMIT 1", Long.class);
        jdbcTemplate.update("INSERT INTO reservation (name, date, time_id) VALUES ('가현', '2026-05-01', ?)", timeId);
        Long id = jdbcTemplate.queryForObject("SELECT id FROM reservation LIMIT 1", Long.class);

        int updatedRow = reservationUpdatingRepository.delete(id);

        assertThat(updatedRow).isEqualTo(1);
        Integer count = jdbcTemplate.queryForObject("SELECT count(*) FROM reservation WHERE id = ?", Integer.class, id);
        assertThat(count).isEqualTo(0);
    }
}