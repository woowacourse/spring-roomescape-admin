package roomescape.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.dto.ReservationTimeRequest;
import roomescape.dto.ReservationTimeResponse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationTimeServiceTest {

    @Autowired
    private ReservationTimeService reservationTimeService;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void add() {
        // given
        ReservationTimeRequest reservationTimeRequest = new ReservationTimeRequest(LocalTime.of(10, 0));

        // when
        reservationTimeService.add(reservationTimeRequest);

        // then
        List<ReservationTimeResponse> reservationTimeResponses = reservationTimeService.findAll();
        assertThat(reservationTimeResponses).containsExactly(new ReservationTimeResponse(1, LocalTime.of(10, 0)));
    }

    @Test
    void findAll() {
        // given
        jdbcTemplate.update("INSERT INTO reservation_time (start_at) VALUES (?)", "10:00");

        // when
        List<ReservationTimeResponse> reservationTimeResponses = reservationTimeService.findAll();

        // then
        assertThat(reservationTimeResponses).containsExactly(new ReservationTimeResponse(1, LocalTime.of(10, 0)));
    }

    @Test
    void delete() {
        // given
        jdbcTemplate.update("INSERT INTO reservation_time (start_at) VALUES (?)", "10:00");

        // when
        reservationTimeService.delete(1);

        // then
        List<ReservationTimeResponse> reservationTimeResponses = reservationTimeService.findAll();
        assertThat(reservationTimeResponses).doesNotContain(new ReservationTimeResponse(1, LocalTime.of(10, 0)));
    }
}
