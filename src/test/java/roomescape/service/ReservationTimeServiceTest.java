package roomescape.service;

import io.restassured.RestAssured;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.exception.ReservationExistsForTimeException;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationTimeServiceTest {

    @Autowired
    ReservationTimeService reservationTimeService;
    @Autowired
    JdbcTemplate jdbcTemplate;
    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void 예약이_등록되어_있는_예약시간은_삭제할_수_없다() {
        jdbcTemplate.update("insert into reservation_time (start_at) values ('10:00')");
        jdbcTemplate.update("insert into reservation (name, date, time_id) values ('조앤', '2023-10-23', 1)");
        long timeId = 1;

        Assertions.assertThatCode(() -> reservationTimeService.deleteTime(timeId))
                .isInstanceOf(ReservationExistsForTimeException.class)
                .hasMessage("해당 예약시간에 예약이 등록되어 있습니다 (timeId : " + timeId + ")");
    }
}
