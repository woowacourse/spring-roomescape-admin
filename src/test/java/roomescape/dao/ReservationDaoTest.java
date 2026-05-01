package roomescape.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import roomescape.domain.Reservation.Reservation;
import roomescape.domain.Reservation.ReservationCommand;
import roomescape.domain.ReservationTime.ReservationTime;

public class ReservationDaoTest {
    private ReservationDao reservationDao;
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

        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS reservation (" +
                "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                "name VARCHAR(255) NOT NULL, " +
                "date VARCHAR(255) NOT NULL, " +
                "time_id BIGINT, " +
                "FOREIGN KEY (time_id) REFERENCES reservation_time (id))");

        jdbcTemplate.update("INSERT INTO reservation_time (start_at) VALUES (?)", "10:00");
        jdbcTemplate.update("INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)", "브라운", "2023-08-05", 1);
        this.reservationDao = new ReservationDao(jdbcTemplate);
    }

    @AfterEach
    void tearDown() {
        jdbcTemplate.execute("DROP TABLE reservation");
        jdbcTemplate.execute("DROP TABLE reservation_time");
    }

    @Test
    @DisplayName("전체 예약 테스트 정상적으로 가져오는 지 테스트")
    void getReservationTest() {
        List<Reservation> reservations = reservationDao.getAllReservation();

        assertThat(reservations).containsExactly(new Reservation(1, "브라운", "2023-08-05", new ReservationTime(1, "10:00")));
    }

    @Test
    @DisplayName("예약 삭제 정상적으로 작동하는 지 테스트")
    void deleteReservationTest() {
        reservationDao.deleteReservation(1);
        List<Reservation> reservations = reservationDao.getAllReservation();

        assertThat(reservations).isNotIn(new Reservation(1, "브라운", "2023-08-05", new ReservationTime(1, "10:00")));
    }

    @Test
    @DisplayName("예약 추가 정상적으로 작동하는 지 테스트")
    void insertReservationTest() {
        long updatedReservation = reservationDao.insertReservation(new ReservationCommand("테스트", "2023-08-15", 1));
        List<Reservation> reservations = reservationDao.getAllReservation();

        Reservation expectedReservation = new Reservation(2, "테스트", "2023-08-15", new ReservationTime(1, "10:00"));

        assertThat(updatedReservation).isEqualTo(2);
        assertThat(reservations).contains(expectedReservation);
    }
}
