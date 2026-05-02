package roomescape.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import roomescape.controller.dto.TimeRequest;
import roomescape.controller.dto.TimeResponse;
import roomescape.repository.ReservationTimeRepository;
import roomescape.service.ReservationTimeService;

public class ReservationTimeControllerTest {
    ReservationTimeController reservationTimeController;
    JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
        dataSource.setUsername("sa");
        dataSource.setPassword("");

        jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcTemplate.execute("DROP TABLE IF EXISTS reservation");
        jdbcTemplate.execute("DROP TABLE IF EXISTS reservation_time");

        jdbcTemplate.execute("""
                CREATE TABLE reservation_time (
                    id BIGINT NOT NULL AUTO_INCREMENT,
                    start_at VARCHAR(255) NOT NULL,
                    PRIMARY KEY (id),
                    UNIQUE (start_at)
                )
                """);
        jdbcTemplate.execute("""
                CREATE TABLE reservation (
                    id      BIGINT       NOT NULL AUTO_INCREMENT,
                    name    VARCHAR(255) NOT NULL,
                    date    VARCHAR(255) NOT NULL,
                    time_id BIGINT,
                    PRIMARY KEY (id),
                    FOREIGN KEY (time_id) REFERENCES reservation_time (id)
                )
                """);

        ReservationTimeRepository reservationTimeRepository = new ReservationTimeRepository(jdbcTemplate);
        ReservationTimeService reservationTimeService = new ReservationTimeService(reservationTimeRepository);
        reservationTimeController = new ReservationTimeController(reservationTimeService);
    }

    @Test
    @DisplayName("시간을 추가한다.")
    void createTime() {
        TimeRequest timeRequest = new TimeRequest("10:00");

        ResponseEntity<TimeResponse> timeResponse = reservationTimeController.create(timeRequest);

        assertThat(timeResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(timeResponse.getBody().id()).isEqualTo(1L);
        assertThat(timeResponse.getBody().startAt()).isEqualTo("10:00");
    }

    @Test
    @DisplayName("시간이 생성된 상태에서 시간을 조회한다.")
    void findAllTimes_After_Create() {
        reservationTimeController.create(new TimeRequest("10:00"));
        reservationTimeController.create(new TimeRequest("11:00"));

        ResponseEntity<List<TimeResponse>> times = reservationTimeController.findAll();

        assertThat(times.getStatusCode()).isEqualTo(HttpStatus.OK);

        assertThat(times.getBody()).hasSize(2);
        assertThat(times.getBody().get(0).id()).isEqualTo(1L);
        assertThat(times.getBody().get(0).startAt()).isEqualTo("10:00");
        assertThat(times.getBody().get(1).id()).isEqualTo(2L);
        assertThat(times.getBody().get(1).startAt()).isEqualTo("11:00");
    }

    @Test
    @DisplayName("아무 시간도 없는 상태에서 시간을 조회한다.")
    void findAllTimes_Before_Create() {
        ResponseEntity<List<TimeResponse>> times = reservationTimeController.findAll();

        assertThat(times.getStatusCode()).isEqualTo(HttpStatus.OK);

        assertThat(times.getBody()).isEmpty();
    }

    @Test
    @DisplayName("시간을 삭제한다.")
    void deleteTime_After_Create() {
        reservationTimeController.create(new TimeRequest("10:00"));
        reservationTimeController.create(new TimeRequest("11:00"));

        ResponseEntity<Void> deleteResponse = reservationTimeController.delete(1L);

        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(deleteResponse.getBody()).isNull();

        ResponseEntity<List<TimeResponse>> times = reservationTimeController.findAll();

        assertThat(times.getBody()).hasSize(1);
        assertThat(times.getBody().get(0).id()).isEqualTo(2L);
        assertThat(times.getBody().get(0).startAt()).isEqualTo("11:00");
    }
}
