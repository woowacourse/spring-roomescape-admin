package roomescape.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.domain.Time;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.repository.TimeRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationControllerTest {

    @Autowired
    ReservationController controller;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    TimeRepository timeRepository;

    private Long savedTimeId;

    @BeforeEach
    void setUp() {
        Time time = timeRepository.add(new Time(null, "10:00"));
        savedTimeId = time.getId();
    }

    @Test
    @DisplayName("전체 예약에 대해서 조회한다.")
    void findAllReservationsTest() {
        jdbcTemplate.update(
                "INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)",
                "브라운", "2023-08-05", savedTimeId
        );

        List<ReservationResponse> allReservations = controller.findAllReservations();

        assertThat(allReservations).hasSize(1);
        assertThat(allReservations.get(0).getTime().getStartAt()).isEqualTo("10:00");
    }

    @Test
    @DisplayName("예약을 추가한다.")
    void addReservationTest() {
        ReservationRequest request = new ReservationRequest("네오", "2023-08-06", savedTimeId);

        controller.addReservation(request);
        List<ReservationResponse> allReservations = controller.findAllReservations();

        assertThat(allReservations).hasSize(1);
    }

    @Test
    @DisplayName("예약을 삭제한다.")
    void deleteReservationTest() {
        jdbcTemplate.update(
                "INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)",
                "브라운", "2023-08-05", savedTimeId
        );

        Long savedReservationId = controller.findAllReservations().get(0).getId();
        controller.deleteReservation(savedReservationId);

        List<ReservationResponse> allReservations = controller.findAllReservations();
        assertThat(allReservations).hasSize(0);
    }

    @AfterEach
    void afterEach() {
        jdbcTemplate.update("DELETE FROM reservation");
        jdbcTemplate.update("DELETE FROM reservation_time");
    }
}
