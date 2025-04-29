package roomescape.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.model.Reservation;
import roomescape.model.ReservationTime;

@JdbcTest
class JdbcReservationRepositoryTest {

    private ReservationRepository repository;

    @Autowired
    private DataSource dataSource;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void beforeEach() {
        jdbcTemplate.execute("DROP TABLE reservation IF EXISTS");
        jdbcTemplate.execute("DROP TABLE reservation_time IF EXISTS");
        jdbcTemplate.execute("""
                CREATE TABLE reservation_time(
                    id BIGINT NOT NULL AUTO_INCREMENT,
                    start_at VARCHAR(255) NOT NULL,
                    PRIMARY KEY (id)
                );
                """);

        jdbcTemplate.execute("""
                CREATE TABLE reservation(
                    id BIGINT NOT NULL AUTO_INCREMENT,
                    name VARCHAR(255) NOT NULL,
                    date VARCHAR(255) NOT NULL,
                    time_id BIGINT NOT NULL,
                    PRIMARY KEY (id),
                    FOREIGN KEY (time_id) REFERENCES reservation_time(id)
                );
                """);
        repository = new JdbcReservationRepository(dataSource);
    }

    @Test
    @DisplayName("초기에 데이터가 존재하지 않는다.")
    void testInitialRepository() {
        assertThat(repository.findAll()).isEmpty();
    }

    @Test
    @DisplayName("예약을 추가할 수 있다")
    void saveTest() {
        // given
        jdbcTemplate.update("insert into reservation_time(id,start_at) values(?,?)", 1L, "10:00");
        ReservationTime reservationTime = ReservationTime.of(1L, LocalTime.of(10, 0));
        Reservation reservation = Reservation.of("멍구", LocalDate.of(2000, 11, 2),
                reservationTime);

        // when
        repository.save(reservation);

        // then
        assertThat(repository.findAll()).hasSize(1);
    }

    @Test
    @DisplayName("id로 예약을 삭제할 수 있다.")
    void deleteByIdTest() {
        // given
        jdbcTemplate.update("insert into reservation_time(id,start_at) values(?,?)", 1L, "10:00");
        ReservationTime reservationTime = ReservationTime.of(1L, LocalTime.of(10, 0));
        Reservation reservation = Reservation.of("멍구", LocalDate.of(2000, 11, 2),
                reservationTime);
        Reservation reservationEntity = repository.save(reservation);

        // when
        repository.deleteById(reservationEntity.getId());

        // then
        assertThat(repository.findAll()).isEmpty();
    }
}
