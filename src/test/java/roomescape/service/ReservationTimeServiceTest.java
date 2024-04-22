package roomescape.service;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.model.ReservationTime;

@SpringBootTest
class ReservationTimeServiceTest {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    ReservationTimeService reservationTimeService;

    @DisplayName("모든 예약 시간을 반환한다")
    @Test
    void should_return_all_reservation_times() {
        jdbcTemplate.update("INSERT INTO reservation_time (start_at) values (?)", "11:00");
        List<ReservationTime> reservationTimes = reservationTimeService.getReservations();
        Assertions.assertThat(reservationTimes).hasSize(1);
    }
}
