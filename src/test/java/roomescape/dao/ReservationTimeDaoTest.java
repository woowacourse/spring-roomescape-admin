package roomescape.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import roomescape.domain.ReservationTime;
import roomescape.domain.ReservationTimeCommand;

public class ReservationTimeDaoTest {
    private ReservationTimeDao reservationTimeDao;
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");
        this.jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS reservation_time (" +
                "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                "start_at VARCHAR(255) NOT NULL)");

        jdbcTemplate.update("INSERT INTO reservation_time (start_at) VALUES (?)", "10:00");
        this.reservationTimeDao = new ReservationTimeDao(jdbcTemplate);
    }

    @AfterEach
    void tearDown() {
        jdbcTemplate.execute("DROP TABLE reservation_time");
    }

    @Test
    @DisplayName("특정 예약 시간 정상적으로 가져오는 지 테스트")
    void getReservationTimeTest() {
        Optional<ReservationTime> reservationTime = reservationTimeDao.getReservationTime(1);

        assertThat(reservationTime.isPresent()).isTrue();
        assertThat(reservationTime.get()).isEqualTo(new ReservationTime(1, "10:00"));
    }

    @Test
    @DisplayName("존재하지 않는 특정 예약 시간 빈 값으로 가져오는 지 테스트")
    void getInvalidReservationTimeTest() {
        Optional<ReservationTime> reservationTime = reservationTimeDao.getReservationTime(3);

        assertThat(reservationTime.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("전체 예약시간 정상적으로 가져오는 지 테스트")
    void getReservationTimesTest() {
        List<ReservationTime> reservationTimes = reservationTimeDao.getAllReservationTime();

        assertThat(reservationTimes).containsExactly(new ReservationTime(1, "10:00"));
    }

    @Test
    @DisplayName("예약 삭제 정상적으로 작동하는 지 테스트")
    void deleteReservationTest() {
        int deletedCount1 = reservationTimeDao.deleteReservation(1);
        int deletedCount2 = reservationTimeDao.deleteReservation(1);

        assertThat(deletedCount1).isEqualTo(1);
        assertThat(deletedCount2).isEqualTo(0);
    }

    @Test
    @DisplayName("예약 추가 정상적으로 작동하는 지 테스트")
    void insertReservationTest() {
        long updatedReservation = reservationTimeDao.insertReservationTime(new ReservationTimeCommand("12:00"));
        List<ReservationTime> reservations = reservationTimeDao.getAllReservationTime();

        ReservationTime expectedReservation = new ReservationTime(2, "12:00");

        assertThat(updatedReservation).isEqualTo(2);
        assertThat(reservations).contains(expectedReservation);
    }
}
