package roomescape;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.domain.dto.ReservationRequest;
import roomescape.domain.service.ReservationService;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ReservationServiceTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ReservationService reservationService;

    @Test
    void 같은_날짜와_시간에_이미_예약이_존재하면_예외를_던진다() {
        jdbcTemplate.update("INSERT INTO reservation_time (start_at) VALUES (?)", "10:00");
        jdbcTemplate.update("INSERT INTO reservation(name, date, time_id) VALUES (?, ?, ?)",
                "브라운", "2026-05-01", 1L);

        ReservationRequest reservationRequest = new ReservationRequest("브라운", LocalDate.of(2026, 5, 1), 1L);

        assertThatThrownBy(() -> reservationService.save(reservationRequest))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 존재하지_않는_시간대에_예약을_생성하면_예외를_던진다() {
        ReservationRequest reservationRequest = new ReservationRequest("브라운", LocalDate.of(2026, 5, 1), 1L);

        assertThatThrownBy(() -> reservationService.save(reservationRequest))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
