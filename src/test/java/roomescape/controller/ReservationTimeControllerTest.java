package roomescape.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import roomescape.dao.ReservationTimeDAO;
import roomescape.domain.ReservationTime;
import roomescape.service.ReservationTimeService;

import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ReservationTimeControllerTest {
    private ReservationTimeController controller;
    private ReservationTime reservationTime;
    private ResponseEntity<ReservationTime> createResponse;

    @BeforeEach
    void setUp() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");
        dataSource.setUsername("sa");
        dataSource.setPassword("");

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcTemplate.execute("DROP TABLE IF EXISTS reservation");
        jdbcTemplate.execute("DROP TABLE IF EXISTS reservation_time");
        jdbcTemplate.execute("CREATE TABLE reservation_time(id BIGINT AUTO_INCREMENT PRIMARY KEY, start_at VARCHAR(255))");

        ReservationTimeDAO reservationTimeDAO = new ReservationTimeDAO(jdbcTemplate);
        ReservationTimeService reservationTimeService = new ReservationTimeService(reservationTimeDAO);
        controller = new ReservationTimeController(reservationTimeService);

        reservationTime = new ReservationTime(LocalTime.of(15, 0));

        createResponse = controller.create(reservationTime);
    }

    @Test
    @DisplayName("시간을 추가하면 200 코드를 반환한다.")
    void return200OK_When_AddReservationTime() {
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("시간을 조회하면 추가한 시간의 정보를 반환한다.")
    void returnReservationTimeInfo_When_AddReservationTime() {
        ResponseEntity<List<ReservationTime>> readResponse = controller.read();
        List<ReservationTime> reservationTimes = readResponse.getBody();

        assertThat(reservationTimes).isNotNull();
        assertThat(reservationTimes).hasSize(1);
        assertThat(reservationTimes.getFirst().getStartAt()).isEqualTo("15:00");
    }

    @Test
    @DisplayName("시간을 삭제하면 200 코드를 반환한다.")
    void return200OK_When_DeleteReservationTime() {
        ResponseEntity<List<ReservationTime>> readResponse = controller.read();
        List<ReservationTime> reservationTimes = readResponse.getBody();
        Long id = reservationTimes.getFirst().getId();
        int beforeSize = reservationTimes.size();

        ResponseEntity<List<ReservationTime>> deleteResponse = controller.delete(id);

        ResponseEntity<List<ReservationTime>> readAfterResponse = controller.read();
        List<ReservationTime> afterReservationTimes = readAfterResponse.getBody();
        int afterSize = afterReservationTimes.size();

        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(beforeSize).isEqualTo(afterSize + 1);
    }
}
