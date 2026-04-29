package roomescape.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import roomescape.domain.Reservation;

public class ReservationDaoTest {
    private ReservationDao reservationDao;
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.execute("CREATE TABLE reservation (" +
                "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                "name VARCHAR(255), " +
                "date VARCHAR(255), " +
                "time VARCHAR(255))");

        jdbcTemplate.update("INSERT INTO reservation (name, date, time) VALUES (?, ?, ?)", "브라운", "2023-08-05", "15:40");
        this.reservationDao = new ReservationDao(jdbcTemplate);
    }

    @AfterEach
    void tearDown() {
        jdbcTemplate.execute("DROP TABLE reservation");
    }

    @Test
    @DisplayName("전체 예약 테스트 정상적으로 가져오는 지 테스트")
    void getReservationTest() {
        List<Reservation> reservations = reservationDao.getAllReservation();

        assertThat(reservations).containsExactly(new Reservation(1, "브라운", "2023-08-05", "15:40"));
    }

    @Test
    @DisplayName("예약 삭제 정상적으로 작동하는 지 테스트")
    void deleteReservationTest() {
        int deletedCount1 = reservationDao.deleteReservation(1);
        int deletedCount2 = reservationDao.deleteReservation(1);

        assertThat(deletedCount1).isEqualTo(1);
        assertThat(deletedCount2).isEqualTo(0);
    }

    @Test
    @DisplayName("예약 추가 정상적으로 작동하는 지 테스트")
    void insertReservationTest() {
        Reservation updatedReservation = reservationDao.insertReservation(new Reservation(-1, "테스트", "2023-08-15", "17:20"));
        List<Reservation> reservations = reservationDao.getAllReservation();

        Reservation expectedReservation = new Reservation(2, "테스트", "2023-08-15", "17:20");

        assertThat(updatedReservation).isEqualTo(expectedReservation);
        assertThat(reservations).contains(expectedReservation);
    }
}
