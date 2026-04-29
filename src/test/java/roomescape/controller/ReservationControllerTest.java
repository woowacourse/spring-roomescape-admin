package roomescape.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;

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
        jdbcTemplate.execute("""
                CREATE TABLE reservation (
                    id BIGINT NOT NULL AUTO_INCREMENT,
                    name VARCHAR(255) NOT NULL,
                    date VARCHAR(255) NOT NULL,
                    time VARCHAR(255) NOT NULL,
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

        ReservationResponse response = reservationController.create(request);

        assertThat(response.id()).isEqualTo(1L);
        assertThat(response.name()).isEqualTo("브라운");
        assertThat(response.date()).isEqualTo("2026-04-29");
        assertThat(response.time()).isEqualTo("10:30");
    }

    @Test
    @DisplayName("아무런 예약이 없는 상태에서 예약을 조회한다.")
    void findAllReservations_Before_Create() {
        List<ReservationResponse> reservations = reservationController.findAll();

        assertThat(reservations).isEmpty();
    }

    // TODO: POST 기능 만들고 이 부분을 주석 해제
    /*@Test
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
    }*/

    // TODO: DELETE 기능 만들고 이 부분을 주석 해제
    /*@Test
    @DisplayName("예약이 존재하는 상황에서 예약을 삭제한다.")
    void deleteReservation_After_Create() {
        reservationController.create(new ReservationRequest("브라운", "2026-04-29", "10:30"));
        reservationController.create(new ReservationRequest("리사", "2026-04-30", "10:40"));

        reservationController.delete(1L);

        List<ReservationResponse> reservations = reservationController.findAll();

        assertThat(reservations).hasSize(1);
        assertThat(reservations.get(0).id()).isEqualTo(2L);
        assertThat(reservations.get(0).name()).isEqualTo("리사");
    }*/
}
