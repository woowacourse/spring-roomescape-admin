package roomescape.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest
class ReservationTimeServiceTest {

    @Autowired
    ReservationTimeService reservationTimeService;
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Test
    void 예약이_등록되어_있는_예약시간은_삭제할_수_없다() {
        jdbcTemplate.update("insert into reservation_time (start_at) values ('10:00')");
        jdbcTemplate.update("insert into reservation (name, date, time_id) values ('조앤', '2023-10-23', 1)");

        Assertions.assertThatCode(() -> reservationTimeService.deleteTime(1))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
