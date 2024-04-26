package roomescape.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationServiceTest {

    @Autowired
    private ReservationService reservationService;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void findAll() {
        // given
        jdbcTemplate.update("INSERT INTO reservation_time (start_at) VALUES (?)", "10:00");
        jdbcTemplate.update("INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)", "브라운", "2023-08-05", 1);

        // when
        List<ReservationResponse> reservationResponses = reservationService.findAll();

        // then
        assertThat(reservationResponses).containsExactly(new ReservationResponse(1, "브라운", LocalDate.of(2023, 8, 5),
                new ReservationTime(1L, LocalTime.of(10, 0))));
    }

    @Test
    void delete() {
        // given
        jdbcTemplate.update("INSERT INTO reservation_time (start_at) VALUES (?)", "10:00");
        jdbcTemplate.update("INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)", "브라운", "2023-08-05", 1);

        // when
        reservationService.delete(1);

        // then
        List<ReservationResponse> reservationResponses = reservationService.findAll();
        assertThat(reservationResponses).doesNotContain(new ReservationResponse(1, "브라운", LocalDate.of(2023, 8, 5),
                new ReservationTime(1L, LocalTime.of(10, 0))));
    }

    @Test
    void add() {
        // given
        jdbcTemplate.update("INSERT INTO reservation_time (start_at) VALUES (?)", "10:00");
        ReservationRequest reservationRequest = new ReservationRequest("브라운", LocalDate.of(2023, 8, 5), 1);

        // when
        reservationService.add(reservationRequest);

        // then
        List<ReservationResponse> reservationResponses = reservationService.findAll();
        assertThat(reservationResponses).containsExactly(new ReservationResponse(1, "브라운", LocalDate.of(2023, 8, 5),
                new ReservationTime(1L, LocalTime.of(10, 0))));
    }
}
