package roomescape.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.model.ReservationTime;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationTimeDAOTest {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    ReservationTimeDAO reservationTimeDAO;

    @BeforeEach
    void setUp() {
        jdbcTemplate.execute("DROP TABLE reservation IF EXISTS");
        jdbcTemplate.execute("DROP TABLE reservation_time IF EXISTS");
        jdbcTemplate.execute("""
                CREATE TABLE reservation_time
                (
                    id   BIGINT       NOT NULL AUTO_INCREMENT,
                    start_at VARCHAR(255) NOT NULL,
                    PRIMARY KEY (id)
                );
                """
        );
        jdbcTemplate.execute("""
                CREATE TABLE reservation
                (
                    id   BIGINT       NOT NULL AUTO_INCREMENT,
                    name VARCHAR(255) NOT NULL,
                    date VARCHAR(255) NOT NULL,
                    time_id BIGINT,
                    PRIMARY KEY (id),
                    FOREIGN KEY (time_id) REFERENCES reservation_time (id)
                );
                """
        );
        jdbcTemplate.update("INSERT INTO reservation_time (start_at) values (?)", "10:00");
        jdbcTemplate.update("INSERT INTO reservation_time (start_at) values (?)", "11:00");
    }

    @DisplayName("모든 예약 시간을 조회한다")
    @Test
    void should_get_reservation_times() {
        List<ReservationTime> reservationTimes = reservationTimeDAO.findAllReservations();
        assertThat(reservationTimes).hasSize(2);
    }

    @DisplayName("아이디에 해당하는 예약 시간을 조회한다.")
    @Test
    void should_get_reservation_time() {
        List<ReservationTime> reservationTimes = reservationTimeDAO.findAllReservations();
        ReservationTime reservationTime = reservationTimeDAO.findReservationTime(1);
        assertThat(reservationTime.getStartAt()).isEqualTo(LocalTime.of(10, 0));
    }

    @DisplayName("예약 시간을 추가한다")
    @Test
    void should_add_reservation_time() {
        long id = reservationTimeDAO.addReservationTime(new ReservationTime(LocalTime.of(12, 0)));
        Integer count = jdbcTemplate.queryForObject("SELECT count(1) from reservation_time", Integer.class);
        assertThat(count).isEqualTo(3);
    }

    @DisplayName("예약 시간을 삭제한다")
    @Test
    void should_delete_reservation_time() {
        reservationTimeDAO.deleteReservationTime(1);
        Integer count = jdbcTemplate.queryForObject("SELECT count(1) from reservation_time", Integer.class);
        assertThat(count).isEqualTo(1);
    }
}
