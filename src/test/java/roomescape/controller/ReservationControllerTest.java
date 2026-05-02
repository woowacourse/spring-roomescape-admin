package roomescape.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import roomescape.controller.dto.ReservationRequest;
import roomescape.controller.dto.ReservationResponse;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationTimeRepository;

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
                CREATE TABLE reservation_time (
                    id BIGINT NOT NULL AUTO_INCREMENT,
                    start_at VARCHAR(255) NOT NULL,
                    PRIMARY KEY (id)
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
                );
                """);

        ReservationRepository reservationRepository = new ReservationRepository(jdbcTemplate);
        ReservationTimeRepository reservationTimeRepository = new ReservationTimeRepository(jdbcTemplate);
        reservationController = new ReservationController(reservationRepository, reservationTimeRepository);
    }

    @Test
    @DisplayName("예약을 생성한다.")
    void makeReservation() {
        insertTime("10:00");
        ReservationRequest request = new ReservationRequest(
                "브라운",
                "2026-04-29",
                1L
        );

        ResponseEntity<ReservationResponse> reservationResponse = reservationController.create(request);

        assertThat(reservationResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(reservationResponse.getBody().id()).isEqualTo(1L);
        assertThat(reservationResponse.getBody().name()).isEqualTo("브라운");
        assertThat(reservationResponse.getBody().date()).isEqualTo("2026-04-29");
        assertThat(reservationResponse.getBody().time().id()).isEqualTo(1L);
        assertThat(reservationResponse.getBody().time().startAt()).isEqualTo("10:00");
    }

    @Test
    @DisplayName("아무런 예약이 없는 상태에서 예약을 조회한다.")
    void findAllReservations_Before_Create() {
        ResponseEntity<List<ReservationResponse>> reservations = reservationController.findAll();

        assertThat(reservations.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(reservations.getBody()).isEmpty();
    }

    @Test
    @DisplayName("예약이 생성된 상태에서 예약을 조회한다.")
    void findAllReservations_After_Create() {
        insertTime("10:00");
        insertTime("11:00");
        reservationController.create(new ReservationRequest("브라운", "2026-04-29", 1L));
        reservationController.create(new ReservationRequest("리사", "2026-04-30", 2L));

        ResponseEntity<List<ReservationResponse>> reservations = reservationController.findAll();

        assertThat(reservations.getStatusCode()).isEqualTo(HttpStatus.OK);

        assertThat(reservations.getBody()).hasSize(2);
        assertThat(reservations.getBody().get(0).id()).isEqualTo(1L);
        assertThat(reservations.getBody().get(0).name()).isEqualTo("브라운");
        assertThat(reservations.getBody().get(0).date()).isEqualTo("2026-04-29");
        assertThat(reservations.getBody().get(0).time().id()).isEqualTo(1L);
        assertThat(reservations.getBody().get(0).time().startAt()).isEqualTo("10:00");

        assertThat(reservations.getBody().get(1).id()).isEqualTo(2L);
        assertThat(reservations.getBody().get(1).name()).isEqualTo("리사");
        assertThat(reservations.getBody().get(1).date()).isEqualTo("2026-04-30");
        assertThat(reservations.getBody().get(1).time().id()).isEqualTo(2L);
        assertThat(reservations.getBody().get(1).time().startAt()).isEqualTo("11:00");
    }

    @Test
    @DisplayName("예약이 존재하는 상황에서 예약을 삭제한다.")
    void deleteReservation_After_Create() {
        insertTime("10:00");
        insertTime("11:00");
        reservationController.create(new ReservationRequest("브라운", "2026-04-29", 1L));
        reservationController.create(new ReservationRequest("리사", "2026-04-30", 2L));

        ResponseEntity<Void> response = reservationController.delete(1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(response.getBody()).isNull();

        ResponseEntity<List<ReservationResponse>> reservations = reservationController.findAll();

        assertThat(reservations.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(reservations.getBody()).hasSize(1);
        assertThat(reservations.getBody().get(0).id()).isEqualTo(2L);
        assertThat(reservations.getBody().get(0).name()).isEqualTo("리사");
    }

    @Test
    @DisplayName("예약이 존재하지 않는 상황에서 예약을 삭제하면 예외가 발생한다.")
    void throwException_When_DeleteEmptyReservation() {
        assertThatThrownBy(() -> reservationController.delete(1L))
                .isInstanceOf(IllegalArgumentException.class);
    }

    private void insertTime(String startAt) {
        jdbcTemplate.update("INSERT INTO reservation_time (start_at) VALUES (?)", startAt);
    }
}
