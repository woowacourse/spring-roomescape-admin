/*
package roomescape.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.domain.Reservation;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationControllerTest {

    @Autowired
    ReservationController controller;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Test
    @DisplayName("전체 예약에 대해서 조회한다.")
    void findAllReservationsTest() {
        jdbcTemplate.update(
                "INSERT INTO reservation (name, date, time) VALUES (?, ?, ?)",
                "브라운", "2023-08-05", "15:40"
        );

        List<Reservation> allReservations = controller.findAllReservations();

        assertThat(allReservations).hasSize(1);
    }

    @Test
    @DisplayName("예약을 추가한다.")
    void addReservationTest() {
        controller.addReservation(new Reservation(null, "네오", "2023-08-06", "15:41"));

        List<Reservation> allReservations = controller.findAllReservations();assertThat

        assertThat(allReservations).hasSize(1);
    }

    @Test
    @DisplayName("예약을 삭제한다.")
    void deleteReservationTest() {
        jdbcTemplate.update(
                "INSERT INTO reservation (name, date, time) VALUES (?, ?, ?)",
                "브라운", "2023-08-05", "15:40"
        );

        controller.deleteReservation(1L);

        List<Reservation> allReservations = controller.findAllReservations();

        assertThat(allReservations).hasSize(0);
    }
}
*/
