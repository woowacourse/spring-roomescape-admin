package roomescape;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.domain.service.ReservationService;
import roomescape.domain.service.ReservationTimeService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ReservationTimeServiceTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ReservationTimeService reservationTimeService;

    @Test
    void 예약_시간대를_삭제하면_해당_시간대의_예약도_함께_삭제된다() {
        jdbcTemplate.update("INSERT INTO reservation_time (start_at) VALUES (?)", "10:00");
        jdbcTemplate.update("INSERT INTO reservation(name, date, time_id) VALUES (?, ?, ?)",
                "브라운", "2026-05-01", 1L);

        reservationTimeService.delete(1L);

        int reservationCount = reservationService.getAll().size();
        assertThat(reservationCount).isEqualTo(0);
    }

    @Test
    void 존재하지_않는_예약_시간대를_삭제하면_예외를_던진다() {
        assertThatThrownBy(() -> reservationTimeService.delete(1L))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
