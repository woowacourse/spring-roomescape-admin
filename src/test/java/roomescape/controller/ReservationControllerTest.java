package roomescape.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationControllerTest {

    @Autowired
    ReservationController controller;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        // FK 의존: 예약은 시간이 있어야 추가 가능
        jdbcTemplate.update("INSERT INTO reservation_time (start_at) VALUES (?)", "10:00");
    }

    @Test
    @DisplayName("전체 예약에 대해서 조회한다.")
    void findAllReservationsTest() {
        jdbcTemplate.update(
                "INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)",
                "브라운", "2023-08-05", 1L
        );

        List<Reservation> allReservations = controller.findAllReservations();

        assertThat(allReservations).hasSize(1);
        assertThat(allReservations.get(0).getReservationTime().getStartAt()).isEqualTo("10:00");
    }

    @Test
    @DisplayName("예약을 추가한다.")
    void addReservationTest() {
        ReservationRequest request = new ReservationRequest("네오", "2023-08-06", 1L);

        controller.addReservation(request);

        List<Reservation> allReservations = controller.findAllReservations();
        assertThat(allReservations).hasSize(1);
    }

    @Test
    @DisplayName("예약을 삭제한다.")
    void deleteReservationTest() {
        jdbcTemplate.update(
                "INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)",
                "브라운", "2023-08-05", 1L
        );

        controller.deleteReservation(1L);

        List<Reservation> allReservations = controller.findAllReservations();
        assertThat(allReservations).hasSize(0);
    }
}
