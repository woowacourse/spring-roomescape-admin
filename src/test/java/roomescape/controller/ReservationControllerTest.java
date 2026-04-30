package roomescape.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.dto.TimeRequest;
import roomescape.dto.TimeResponse;

class ReservationControllerTest {
    ReservationController reservationController;
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
                CREATE TABLE reservation (
                    id BIGINT NOT NULL AUTO_INCREMENT,
                    name VARCHAR(255) NOT NULL,
                    date VARCHAR(255) NOT NULL,
                    time VARCHAR(255) NOT NULL,
                    PRIMARY KEY (id)
                )
                """);
        jdbcTemplate.execute("""
                CREATE TABLE reservation_time (
                    id BIGINT NOT NULL AUTO_INCREMENT,
                    start_at VARCHAR(255) NOT NULL,
                    PRIMARY KEY (id)
                )
                """);

        reservationController = new ReservationController(jdbcTemplate);
    }

    @Test
    @DisplayName("예약을 생성한다.")
    void makeReservation() {
        ReservationRequest request = new ReservationRequest(
                "브라운",
                "2026-04-29"
                , "10:30"
        );

        Long reservationId = reservationController.create(request);

        assertThat(reservationId.longValue()).isEqualTo(1L);
    }

    @Test
    @DisplayName("아무런 예약이 없는 상태에서 예약을 조회한다.")
    void findAllReservations_Before_Create() {
        List<ReservationResponse> reservations = reservationController.findAll();

        assertThat(reservations).isEmpty();
    }

    @Test
    @DisplayName("예약이 생성된 상태에서 예약을 조회한다.")
    void findAllReservations_After_Create() {
        reservationController.create(new ReservationRequest("브라운", "2026-04-29", "10:30"));
        reservationController.create(new ReservationRequest("리사", "2026-04-30", "10:40"));

        List<ReservationResponse> reservations = reservationController.findAll();

        assertThat(reservations).hasSize(2);
        assertThat(reservations.get(0).id()).isEqualTo(1L);
        assertThat(reservations.get(0).name()).isEqualTo("브라운");
        assertThat(reservations.get(0).date()).isEqualTo("2026-04-29");
        assertThat(reservations.get(0).time()).isEqualTo("10:30");

        assertThat(reservations.get(1).id()).isEqualTo(2L);
        assertThat(reservations.get(1).name()).isEqualTo("리사");
        assertThat(reservations.get(1).date()).isEqualTo("2026-04-30");
        assertThat(reservations.get(1).time()).isEqualTo("10:40");
    }

    @Test
    @DisplayName("예약이 존재하는 상황에서 예약을 삭제한다.")
    void deleteReservation_After_Create() {
        reservationController.create(new ReservationRequest("브라운", "2026-04-29", "10:30"));
        reservationController.create(new ReservationRequest("리사", "2026-04-30", "10:40"));

        reservationController.delete(1L);

        List<ReservationResponse> reservations = reservationController.findAll();

        assertThat(reservations).hasSize(1);
        assertThat(reservations.get(0).id()).isEqualTo(2L);
        assertThat(reservations.get(0).name()).isEqualTo("리사");
    }

    @Test
    @DisplayName("시간을 추가한다.")
    void createTime() {
        TimeRequest timeRequest = new TimeRequest("10:00");

        TimeResponse timeResponse = reservationController.createTime(timeRequest);

        assertThat(timeResponse.id()).isEqualTo(1L);
        assertThat(timeResponse.startAt()).isEqualTo("10:00");
    }

    @Test
    @DisplayName("시간이 생성된 상태에서 시간을 조회한다.")
    void findAllTimes_After_Create() {
        reservationController.createTime(new TimeRequest("10:00"));
        reservationController.createTime(new TimeRequest("11:00"));

        List<TimeResponse> timeResponses = reservationController.findAllTime();

        assertThat(timeResponses).hasSize(2);

        assertThat(timeResponses.get(0).id()).isEqualTo(1L);
        assertThat(timeResponses.get(0).startAt()).isEqualTo("10:00");

        assertThat(timeResponses.get(1).id()).isEqualTo(2L);
        assertThat(timeResponses.get(1).startAt()).isEqualTo("11:00");
    }

    @Test
    @DisplayName("아무 시간도 없는 상태에서 시간을 조회한다.")
    void findAllTimes_Before_Create() {
        List<TimeResponse> times = reservationController.findAllTime();

        assertThat(times).isEmpty();
    }

    @Test
    @DisplayName("시간을 삭제한다.")
    void deleteTime_After_Create() {
        reservationController.createTime(new TimeRequest("10:00"));
        reservationController.createTime(new TimeRequest("11:00"));

        reservationController.deleteTime(1L);

        List<TimeResponse> times = reservationController.findAllTime();

        assertThat(times).hasSize(1);
        assertThat(times.get(0).id()).isEqualTo(2L);
        assertThat(times.get(0).startAt()).isEqualTo("11:00");
    }

    @Test
    @DisplayName("존재하지 않는 시간을 삭제하면 예외가 발생한다.")
    void deleteTime_NotFound() {
        assertThatThrownBy(() -> reservationController.deleteTime(1L))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
