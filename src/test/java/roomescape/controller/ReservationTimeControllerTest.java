package roomescape.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import roomescape.dto.TimeRequest;
import roomescape.dto.TimeResponse;
import roomescape.repository.ReservationTimeRepository;

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
                    PRIMARY KEY (id)
                )
                """);

        ReservationTimeRepository reservationTimeRepository = new ReservationTimeRepository(jdbcTemplate);
        reservationTimeController = new ReservationTimeController(reservationTimeRepository);
    }

    @Test
    @DisplayName("시간을 추가한다.")
    void createTime() {
        TimeRequest timeRequest = new TimeRequest("10:00");

        TimeResponse timeResponse = reservationTimeController.create(timeRequest);

        assertThat(timeResponse.id()).isEqualTo(1L);
        assertThat(timeResponse.startAt()).isEqualTo("10:00");
    }

    @Test
    @DisplayName("시간이 생성된 상태에서 시간을 조회한다.")
    void findAllTimes_After_Create() {
        reservationTimeController.create(new TimeRequest("10:00"));
        reservationTimeController.create(new TimeRequest("11:00"));

        List<TimeResponse> timeResponses = reservationTimeController.findAll();

        assertThat(timeResponses).hasSize(2);

        assertThat(timeResponses.get(0).id()).isEqualTo(1L);
        assertThat(timeResponses.get(0).startAt()).isEqualTo("10:00");

        assertThat(timeResponses.get(1).id()).isEqualTo(2L);
        assertThat(timeResponses.get(1).startAt()).isEqualTo("11:00");
    }

    @Test
    @DisplayName("아무 시간도 없는 상태에서 시간을 조회한다.")
    void findAllTimes_Before_Create() {
        List<TimeResponse> times = reservationTimeController.findAll();

        assertThat(times).isEmpty();
    }

    @Test
    @DisplayName("시간을 삭제한다.")
    void deleteTime_After_Create() {
        reservationTimeController.create(new TimeRequest("10:00"));
        reservationTimeController.create(new TimeRequest("11:00"));

        reservationTimeController.delete(1L);

        List<TimeResponse> times = reservationTimeController.findAll();

        assertThat(times).hasSize(1);
        assertThat(times.get(0).id()).isEqualTo(2L);
        assertThat(times.get(0).startAt()).isEqualTo("11:00");
    }

    @Test
    @DisplayName("존재하지 않는 시간을 삭제하면 예외가 발생한다.")
    void deleteTime_NotFound() {
        assertThatThrownBy(() -> reservationTimeController.delete(1L))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
